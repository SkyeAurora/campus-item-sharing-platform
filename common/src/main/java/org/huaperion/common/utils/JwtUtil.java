package org.huaperion.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * @Author: Huaperion
 * @Date: 2025/3/9
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@Component
public class JwtUtil {
    /**
     * 携带JWT令牌的HTTP的Header的名称,在实际生产中可读性越差越安全
     */
    @Getter
    // @Value("${jwt.token}")
    private String token = "token_key";

    /**
     * 为JWT基础信息加密和解密的密钥
     * 在实际生产中通常不直接写在配置文件里面。而是通过应用的启动参数传递，并且需要定期修改。
     */
    // @Value("${jwt.secret}")
    private String secret = "CampusSecondHandGoodsTradingPlatformByHuaperion";

    /**
     * JWT令牌的有效时间，单位 分钟
     * - 默认2周
     */
    @Getter
    // @Value("${jwt.expiration}")
    private Long expiration = 2L;

    /**
     * 生成Token令牌
     *
     * @param subject 用户编号
     * @return 令牌Token
     */
    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sid", subject);
        claims.put("created", new Date());
        return getJwtBuilder(claims).compact();
    }

    public boolean validateToken(String token) {
        //如果已经过期返回false
        if (isTokenExpired(token)) {
            return false;
        }

        try {
            final JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(secret))
                    .build();
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO 全局异常捕获
            return false;
            // throw new GlobalException("Token校验失败，非法用户");
        }
    }

    /**
     * 从token中获取Subject
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getSubjectFromToken(String token) {
        String subject;
        try {
            Claims claims = getClaimsFromToken(token);
            subject = claims.getSubject();
        } catch (Exception e) {
            subject = null;
        }
        return subject;
    }

    /**
     * 从token中获取数据声明claim
     *
     * @param token 令牌token
     * @return 数据声明claim
     */
    public Claims getClaimsFromToken(String token) {
        try {
            SecretKey key = getSecretKey(secret);
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("token解析错误", e);
            throw new IllegalArgumentException("Token invalided.");
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token 令牌
     * @return 是否过期：已过期返回true，未过期返回false
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpireFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 获取token的过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public Date getExpireFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * SecretKey 根据 SECRET 的编码方式解码后得到：
     * Base64 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
     * Base64URL 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
     * 未编码：SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
     */
    private static SecretKey getSecretKey(String secret) {
        byte[] encodeKey = Decoders.BASE64.decode(secret);
        // System.err.println("Test log: "+encodeKey.length);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    /**
     * 用claims生成token
     *
     * @param claims 数据声明，用来创建payload的私有声明，subject只是claims的一部分
     * @return token 令牌
     */
    private JwtBuilder getJwtBuilder(Map<String, Object> claims) {
        SecretKey key = getSecretKey(secret);
        //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //两种方式等价

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        // 添加payload声明
        return Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(uuid)
                // 你也可以改用你喜欢的算法，支持的算法详见：https://github.com/jwtk/jjwt#features
                // SignatureAlgorithm.HS256：指定签名的时候使用的签名算法，也就是header那部分
                .signWith(key, SignatureAlgorithm.HS256)
                // iat: jwt的签发时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration * 1000 * 60));
    }
}

