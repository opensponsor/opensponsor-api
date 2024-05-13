package com.opensponsor.enums;

public enum E_CURRENCY_FORMATS {
    EUR("€"),
    GBP("£"),
    INR("₹"),
    SEK("kr "),
    USD("$"),
    UYU("$U "),
    CNY("¥ ");

    public final String label;
    private E_CURRENCY_FORMATS(String label) {
        this.label = label;
    }
}
