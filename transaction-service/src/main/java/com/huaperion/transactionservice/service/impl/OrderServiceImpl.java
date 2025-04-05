package com.huaperion.transactionservice.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huaperion.transactionservice.config.AlipayKeyConfig;
import com.huaperion.transactionservice.model.dto.ConfirmOrderDTO;
import org.huaperion.common.constant.OrderStatusEnum;
import com.huaperion.transactionservice.feign.ItemServiceClient;
import com.huaperion.transactionservice.mapper.OrderMapper;
import com.huaperion.transactionservice.model.dto.CancelOrderDTO;
import com.huaperion.transactionservice.model.dto.CreateOrderDTO;
import com.huaperion.transactionservice.model.entity.Order;
import com.huaperion.transactionservice.model.vo.CreateOrderVO;
import com.huaperion.transactionservice.model.vo.MyOrderVO;
import com.huaperion.transactionservice.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.huaperion.common.exception.ErrorCode;
import org.huaperion.common.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: Huaperion
 * @Date: 2025/4/1
 * @Blog: blog.huaperion.cn
 * @Description:
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    public static final String FORMAT = "JSON";

    public static final String CHARSET = "UTF-8";

    public static final String SIGN_TYPE = "RSA2";

    @Autowired
    private AlipayKeyConfig alipayKeyConfig;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ItemServiceClient itemServiceClient;

    @Override
    public Result<CreateOrderVO> createOrder(CreateOrderDTO createOrderDTO) {
        // 修改物品状态 2 -> 3
        itemServiceClient.changeProductStatus(createOrderDTO.getItemId(), 3);

        Order order = new Order();
        BeanUtils.copyProperties(createOrderDTO, order);
        order.setId(generateOrderId());

        order.setStatus(OrderStatusEnum.PENDING_PAYMENT.getCode()); // 待付款状态

        // TODO 引入服务费与折扣策略（信用分相关）
        order.setServiceFee(new BigDecimal("5"));
        order.setDiscount(new BigDecimal("0"));

        order.setTotalAmount(order.getAmount().add(order.getServiceFee()).subtract(order.getDiscount()));

        orderMapper.insert(order);
        CreateOrderVO createOrderVO = new CreateOrderVO();
        BeanUtils.copyProperties(order, createOrderVO);
        return Result.success(createOrderVO, "创建订单成功，请支付");
    }

    @Override
    public Result<IPage<MyOrderVO>> getMyOrders(Page<MyOrderVO> page, Integer buyerId) {
        return Result.success(orderMapper.selectMyOrders(page, buyerId), "成功获取我的订单");
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result cancelOrder(CancelOrderDTO cancelOrderDTO) {
        Order order = orderMapper.selectById(cancelOrderDTO.getOrderId());

        // 修改物品状态 3 -> 2
        itemServiceClient.changeProductStatus(order.getItemId(), 2);

        LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(Order::getId, cancelOrderDTO.getOrderId())
                .set(Order::getStatus, OrderStatusEnum.CANCELED.getCode())
                .set(Order::getCancelReason, cancelOrderDTO.getCancelReason())
                .set(Order::getCompletionTime, LocalDateTime.now().toString());
        orderMapper.update(null, updateWrapper);

        // 状态为 TO_BE_SHIPPED 的订单需要进行退款
        if (order.getStatus() == OrderStatusEnum.TO_BE_SHIPPED.getCode()) {
            // 退款流程
            try {
                aliPayRefund(order.getId(), order.getPayNo(), order.getAmount());
            } catch (AlipayApiException e) {
                // 记录日志
                log.error("退款失败，订单ID：{}, 错误信息：{}", order.getId(), e.getMessage(), e);

                // 回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.error(ErrorCode.ALIPAY_REFUND_FAIL.getCode(), ErrorCode.ALIPAY_REFUND_FAIL.getMessage());
            }
        }

        return Result.success("成功取消订单");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelExpiredOrders() {
        // 订单过期时间为24h
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getStatus, OrderStatusEnum.PENDING_PAYMENT.getCode()).lt(Order::getCreateTime, cutoffTime);
        List<Order> expiredOrders = orderMapper.selectList(queryWrapper);

        for (Order order : expiredOrders) {
            log.info("定时任务执行取消订单：{}", order.getId());
            order.setStatus(OrderStatusEnum.CANCELED.getCode());
            orderMapper.updateById(order);
            // 修改物品状态 3 -> 2
            itemServiceClient.changeProductStatus(order.getItemId(), 2);
        }
    }

    @Override
    public void payOrder(String orderId, String gmtPayment, String tradeNo, String payMethod) {
        LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Order::getId, orderId)
                .set(Order::getStatus, OrderStatusEnum.TO_BE_SHIPPED.getCode())
                .set(Order::getPaymentTime, gmtPayment)
                .set(Order::getPayNo, tradeNo)
                .set(Order::getPaymentMethod, payMethod);
        orderMapper.update(null, updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result confirmOrder(ConfirmOrderDTO confirmOrderDTO) {
        // 更新订单
        Order order = orderMapper.selectById(confirmOrderDTO.getOrderId());
        order.setStatus(OrderStatusEnum.COMPLETED.getCode());
        order.setRating(confirmOrderDTO.getRating());
        order.setComment(confirmOrderDTO.getComment());
        orderMapper.updateById(order);
        // 转账
        try {
            alipayTransfer(order.getId(), order.getAmount(), order.getItemTitle());
        } catch (Exception e) {
            // 记录日志
            log.error("退款失败，订单ID：{}, 错误信息：{}", order.getId(), e.getMessage(), e);

            // 回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(ErrorCode.ALIAPY_TRANSFER_FAIL.getCode(), ErrorCode.ALIAPY_TRANSFER_FAIL.getMessage());
        }
        return Result.success("成功确认订单，并完成转账");
    }


    public static String generateOrderId() {
        // 获取当前时间（精确到毫秒）
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        // 生成 15 位随机数
        Random random = new Random();
        StringBuilder randomDigits = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            randomDigits.append(random.nextInt(10)); // 0-9 的随机数字
        }

        // 组合成 32 位订单号
        return timestamp + randomDigits;
    }

    /**
     * 支付宝退款
     */
    public void aliPayRefund(String orderId, String tradeNo, BigDecimal refundAmount) throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, alipayKeyConfig.getAppId(), alipayKeyConfig.getAppPrivateKey(), FORMAT, CHARSET, alipayKeyConfig.getAlipayPublicKey(), SIGN_TYPE);

        // 构造请求参数以调用接口
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();

        // 设置商户订单号
        model.setOutTradeNo(orderId);

        // 设置支付宝交易号
        model.setTradeNo(tradeNo);

        // 设置退款金额
        model.setRefundAmount(refundAmount.toString());

        // 设置退款原因说明
        model.setRefundReason("用户取消订单退款：" + orderId);

        model.setOutRequestNo("RF" + orderId);

        request.setBizModel(model);
        // 第三方代调用模式下请设置app_auth_token
        // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

        AlipayTradeRefundResponse response = alipayClient.execute(request);
        log.info(response.getBody());

        if (response.isSuccess()) {
            log.info("平台到用户支付宝退款成功,订单：{}", orderId);
        } else {
            log.error("平台到用户支付宝退款失败，订单：{}", orderId);

            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            log.error("response详情：{}", diagnosisUrl);
            // System.out.println(diagnosisUrl);
        }
    }

    /**
     * 支付宝转账
     */
    public void alipayTransfer(String orderId, BigDecimal amount, String orderTitle) throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, alipayKeyConfig.getAppId(), alipayKeyConfig.getAppPrivateKey(), FORMAT, CHARSET, alipayKeyConfig.getAlipayPublicKey(), SIGN_TYPE);

        // 构造请求参数以调用接口
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();

        // 设置商家侧唯一订单号
        model.setOutBizNo(orderId);
        // 设置订单总金额
        model.setTransAmount(amount.toString());

        // 设置描述特定的业务场景
        model.setBizScene("DIRECT_TRANSFER");

        // 设置业务产品码
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");

        // 设置转账业务的标题
        model.setOrderTitle(orderTitle);

        // 设置收款方信息
        Participant payeeInfo = new Participant();
        payeeInfo.setCertType("IDENTITY_CARD");
        payeeInfo.setCertNo("614578197409058408");
        payeeInfo.setIdentity("2088722063098865");
        payeeInfo.setName("jyojqb0793");
        payeeInfo.setIdentityType("ALIPAY_USER_ID");
        model.setPayeeInfo(payeeInfo);

        // 设置业务备注
        model.setRemark("校园二手物品交易平台到用户业务转账");

        // 设置转账业务请求的扩展参数
        model.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");

        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = alipayClient.execute(request);
        log.info(response.getBody());

        if (response.isSuccess()) {
            log.info("平台到用户支付宝转账成功,订单：{}", orderId);
        } else {
            log.error("平台到用户支付宝转账失败，订单：{}", orderId);

            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            log.error("response详情：{}", diagnosisUrl);
            // System.out.println(diagnosisUrl);
        }
    }
}
