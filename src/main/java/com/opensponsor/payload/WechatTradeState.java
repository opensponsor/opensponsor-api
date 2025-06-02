package com.opensponsor.payload;

import com.wechat.pay.java.service.payments.model.Transaction;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema
public class WechatTradeState extends Transaction {
}
