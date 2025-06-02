package com.opensponsor.utils.wechat;

import com.alipay.api.diagnosis.DiagnosisUtils;
import com.opensponsor.config.WechatPayProperties;
import com.opensponsor.entitys.Order;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import com.opensponsor.enums.E_ORDER_STATUS;
import com.opensponsor.enums.E_PAYMENT_METHOD;
import com.opensponsor.payload.WechatPayOrderResult;
import com.opensponsor.utils.FileTools;
import com.opensponsor.utils.OrderTools;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.QueryOrderByOutTradeNoRequest;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class WechatPayTrade {
    private static final String merchantId = FileTools.getUserHomeConfig("wechatPay/merchantId.txt");

    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    @Inject
    WechatPayProperties wechatPayProperties;

    private static final Logger log = LoggerFactory.getLogger(WechatPayTrade.class);

    public WechatPayOrderResult generateOrder(Tier tier, User user) {
        String merchantId = FileTools.getUserHomeConfig("wechatPay/merchantId.txt");
        String appId = FileTools.getUserHomeConfig("wechatPay/appId.txt");
        String tradeNo = OrderTools.persistOrder(tier, E_PAYMENT_METHOD.WE_CHAT_PAY, user);

        // 构建service
        NativePayService service = new NativePayService.Builder().config(getConfig()).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(tier.amount.intValue() * 100);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription(tier.name);
        // 报文文档 POST：https://pay.weixin.qq.com/doc/global/v3/zh/4012354579?from=https%3A%2F%2Fpay.weixin.qq.com%2Fwiki%2Fdoc%2Fapi_external%2Fch%2Fapis%2Fchapter3_5_11.shtml
        request.setNotifyUrl(wechatPayProperties.notifyUrl());
        request.setOutTradeNo(tradeNo);
        // 调用下单方法，得到应答
        PrepayResponse response = service.prepay(request);

        log.info("getCodeUrl");
        log.info(response.getCodeUrl());
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        // tradeNo
        return new WechatPayOrderResult(response.getCodeUrl(), tradeNo);
    }

    /**
     * 查询订单 https://pay.weixin.qq.com/doc/global/v3/zh/4012354570#2.-%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0
     * @param outTradeNo
     * @return
     */
    @Transactional
    public Transaction.TradeStateEnum queryOrderForOutTradeNo (String outTradeNo) {
        String merchantId = FileTools.getUserHomeConfig("wechatPay/merchantId.txt");
        String appId = FileTools.getUserHomeConfig("wechatPay/appId.txt");

        // 构建service
        JsapiServiceExtension service =
            new JsapiServiceExtension.Builder()
                .config(getConfig())
                .signType("RSA") // 不填默认为RSA
                .build();

        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(merchantId);
        request.setOutTradeNo(outTradeNo);
        Transaction response = service.queryOrderByOutTradeNo(request);

        if(response.getTradeState().equals(Transaction.TradeStateEnum.SUCCESS)) {
            Optional<Order> orderO = Order.find("outTradeNo = ?1", outTradeNo).firstResultOptional();
            if(orderO.isPresent()) {
                Order order = orderO.get();
                order.tradeNo = response.getTransactionId();
                order.status = E_ORDER_STATUS.PAID;
                order.persistAndFlush();
            }
        }

        return response.getTradeState();
    }

    private Config getConfig() {
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(WechatPayTrade.merchantId)
            .privateKey(FileTools.getUserHomeConfig("wechatPay/apiclient_key.pem"))
            .merchantSerialNumber(FileTools.getUserHomeConfig("wechatPay/merchantSerialNumber.txt"))
            .apiV3Key(FileTools.getUserHomeConfig("wechatPay/apiV3Key.txt"))
            .build();
    }
}
