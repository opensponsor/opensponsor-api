package com.opensponsor.config;


import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "wechatPay")
public interface WechatPayProperties {
    String notifyUrl();
}
