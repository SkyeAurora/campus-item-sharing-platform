package org.huaperion.gatewayservice.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.exception.ErrorCode;
import org.huaperion.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Huaperion
 * @Date: 2025/3/10
 * @Blog: blog.huaperion.cn
 * @Description: 全局过滤器
 */
@Slf4j
@Component
public class GlobalFilterConfig implements GlobalFilter, Ordered {

    private static final String HEADER_NAME = "Auth-Token";

    @Resource
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("====== GateWay Global filter ======");

        // 获取请求对象与响应对象
        ServerHttpRequest request = exchange.getRequest();

        // 获取请求地址
        String url = request.getURI().getPath();

        //获取Token
        String token = request.getHeaders().getFirst(HEADER_NAME);

        // 判断是否为白名单请求，以及内置的不需要验证的请求 如 Login 请求
        if (url.startsWith("/user/login") || url.startsWith("/user/register")) {
            return chain.filter(exchange);
        }

        if (!StringUtils.hasLength(token)) {
            log.error("GateWay Global filter: token is empty");
            return unAuthorized(exchange, ErrorCode.AUTH_TOKEN_EMPTY);
        }

        // 若当前请求包含Token令牌，验证Token合法性(Redis)
        if (!redisTemplate.hasKey(token)) {
            log.error("GateWay Global filter: Redis does not contain token: {}", token);
            // 返回未登录的自定义错误
            return unAuthorized(exchange, ErrorCode.USER_NOT_LOGIN);
        }

        ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, token).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     *
     */
    private Mono<Void> unAuthorized(ServerWebExchange exchange, ErrorCode err) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorMsg = "{\n" +
                "    \"code\": " + err.getCode() + ",\n" +
                "    \"msg\": \"" + err.getMessage() + "\", \n" +
                "    \"data\": {}\n" +
                "}";
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(errorMsg.getBytes())));
    }
}
