package com.huaperion.transactionservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.huaperion.transactionservice.config.AlipayKeyConfig;
import com.huaperion.transactionservice.model.entity.Order;
import com.huaperion.transactionservice.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Huaperion
 * @Date: 2025/4/2
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    public static final String FORMAT = "JSON";

    public static final String CHARSET = "UTF-8";

    public static final String SIGN_TYPE = "RSA2";

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private AlipayKeyConfig alipayKeyConfig;

    @GetMapping("/pay")
    public void pay(String orderId, HttpServletResponse response) throws Exception {
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            return;
        }

        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, alipayKeyConfig.getAppId(), alipayKeyConfig.getAppPrivateKey(), FORMAT, CHARSET, alipayKeyConfig.getAlipayPublicKey(), SIGN_TYPE);

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(alipayKeyConfig.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", order.getTotalAmount());
        bizContent.put("subject", "校园二手物品交易支付：" + order.getItemTitle());
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        request.setReturnUrl(alipayKeyConfig.getReturnUrl() + orderId);

        String form = "";

        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=" + CHARSET);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @PostMapping("/notify")
    public void payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String key : requestParams.keySet()) {
                params.put(key, request.getParameter(key));
            }

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayKeyConfig.getAlipayPublicKey(), "UTF-8");

            if (checkSignature) {
                log.info("交易名称：{}", params.get("subject"));
                log.info("交易状态：{}", params.get("trade_status"));
                log.info("支付宝交易凭证号：{}", params.get("trade_no"));
                log.info("商户订单号：{}", params.get("out_trade_no"));
                log.info("交易金额：{}", params.get("total_amount"));
                log.info("买家在支付宝唯一Id：{}", params.get("buyer_id"));
                log.info("买家付款时间：{}", params.get("gmt_payment"));
                log.info("买家付款金额：{}", params.get("buyer_pay_amount"));

                // 更新订单状态为已支付
                orderService.payOrder(params.get("out_trade_no"), params.get("gmt_payment"), params.get("alipay_trade_no"), "Alipay");
            }
        }
    }
}
