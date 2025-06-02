package com.opensponsor.payload;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema
public class WechatPayOrderResult {
    @Schema(required = true, description = "qrcode url")
    public String codeUrl;

    @Schema(required = true, description = "outTradeNo")
    public String outTradeNo;

    public WechatPayOrderResult(String codeUrl, String tradeNo) {
        this.codeUrl = codeUrl;
        this.outTradeNo = tradeNo;
    }
}
