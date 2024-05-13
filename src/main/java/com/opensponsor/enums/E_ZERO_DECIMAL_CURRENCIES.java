package com.opensponsor.enums;

/**
 * Zero-decimal currencies for Stripe; https://stripe.com/docs/currencies#zero-decimal
 */
public enum E_ZERO_DECIMAL_CURRENCIES {
    BIF("BIF"),
    CLP("CLP"),
    DJF("DJF"),
    GNF("GNF"),
    JPY("JPY"),
    KMF("KMF"),
    KRW("KRW"),
    MGA("MGA"),
    PYG("PYG"),
    RWF("RWF"),
    UGX("UGX"),
    VND("VND"),
    VUV("VUV"),
    XAF("XAF"),
    XOF("XOF"),
    XPF("XPF");

    public final String label;

    private E_ZERO_DECIMAL_CURRENCIES(String label) {
        this.label = label;
    }
}
