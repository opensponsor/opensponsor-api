package com.opensponsor.utils.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.*;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.opensponsor.config.AlipayProperties;
import com.opensponsor.entitys.Order;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import com.opensponsor.enums.E_ORDER_STATUS;
import com.opensponsor.enums.E_PAYMENT_METHOD;
import com.opensponsor.payload.TradePagePayBodyForAliPay;
import com.opensponsor.utils.FileTools;
import com.opensponsor.utils.OrderTools;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class AliPayTrade {
    @Inject
    AlipayProperties alipayProperties;

    private static final Logger log = LoggerFactory.getLogger(AliPayTrade.class);

    public String generateOrder(Tier tier, User user) throws AlipayApiException {
        String num = String.valueOf(tier.amount);
        String tradeNo = OrderTools.persistOrder(tier, E_PAYMENT_METHOD.ALI_PAY, user);

        AlipayClient alipayClient = new DefaultAlipayClient(AliPayTrade.getConfig());
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(tradeNo);
        model.setTotalAmount(num);
        model.setSubject(tier.name);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        ExtUserInfo extUserInfo = new ExtUserInfo();
        extUserInfo.setMobile(user.phoneNumber);
        model.setExtUserInfo(extUserInfo);
        request.setBizModel(model);

        if(!alipayProperties.notifyUrl().isEmpty()) {
            request.setNotifyUrl(alipayProperties.notifyUrl());
        }
        request.setReturnUrl(alipayProperties.returnUrl());

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
        // 如果需要返回GET请求，请使用
        // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        String pageRedirectionData = response.getBody();
        if (response.isSuccess()) {
            return pageRedirectionData;
        } else {
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            log.error(diagnosisUrl);
            return null;
        }
    }

     public static AlipayTradeQueryResponse queryTrade(TradePagePayBodyForAliPay data) throws AlipayApiException {
         // 初始化SDK
         AlipayClient alipayClient = new DefaultAlipayClient(getConfig());

         // 构造请求参数以调用接口
         AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
         AlipayTradeQueryModel model = new AlipayTradeQueryModel();

         // 设置订单支付时传入的商户订单号
         model.setOutTradeNo(data.out_trade_no);

         // 设置支付宝交易号
         model.setTradeNo(data.trade_no);

         // 设置查询选项
         model.setQueryOptions(List.of("trade_settle_info"));

         request.setBizModel(model);
         // 第三方代调用模式下请设置app_auth_token
         // request.putOtherTextParam("app_auth_token", "<-- 请填写应用授权令牌 -->");

         AlipayTradeQueryResponse response = alipayClient.execute(request);
         return response;
     }

    private static AlipayConfig getConfig() {
        String privateKey  = FileTools.getUserHomeConfig("alipay/applicationPrivateKey.txt");
        String alipayPublicKey = FileTools.getUserHomeConfig("alipay/alipayPublicKey_RSA2.txt");
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId(FileTools.getUserHomeConfig("alipay/appId.txt"));
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        return alipayConfig;
    }
}
