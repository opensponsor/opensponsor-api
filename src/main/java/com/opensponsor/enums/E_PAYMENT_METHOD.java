package com.opensponsor.enums;

public enum E_PAYMENT_METHOD {
    WE_CHAT_PAY("WE_CHAT_PAY"),
    ALI_PAY("ALI_PAY"),
    UNION_PAY("UNION_PAY");

    public final String label;

    private E_PAYMENT_METHOD(String label) {
        this.label = label;
    }
}
