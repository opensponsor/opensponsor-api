package com.opensponsor.utils.wechat;

import com.opensponsor.config.WechatPayProperties;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import com.opensponsor.utils.FileTools;
import com.opensponsor.utils.OrderTools;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;

@ApplicationScoped
public class WechatPayTrade {
    @Inject
    WechatPayProperties wechatPayProperties;

    private static final Logger log = LoggerFactory.getLogger(WechatPayTrade.class);

    public String generateOrder(Tier tier, User user) {
        String merchantId = FileTools.getUserHomeConfig("wechatPay/merchantId.txt");
        String appId = FileTools.getUserHomeConfig("wechatPay/appId.txt");

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
        request.setNotifyUrl(wechatPayProperties.notifyUrl());
        request.setOutTradeNo(OrderTools.generateOrderNo());
        // 调用下单方法，得到应答
        PrepayResponse response = service.prepay(request);
        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
        return response.getCodeUrl();
    }

    private Config getConfig() {
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(FileTools.getUserHomeConfig("wechatPay/merchantId.txt"))
            .privateKeyFromPath(
                 String.format("%s/.opensponsor/%s", System.getenv("HOME"), "wechatPay/apiclient_key.pem")
            )
            .merchantSerialNumber(FileTools.getUserHomeConfig("wechatPay/merchantSerialNumber.txt"))
            .apiV3Key(FileTools.getUserHomeConfig("wechatPay/apiV3Key.txt"))
            .build();
    }
}
