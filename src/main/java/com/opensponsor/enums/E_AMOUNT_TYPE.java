package com.opensponsor.enums;

public enum E_AMOUNT_TYPE {
    FIXED("FIXED"),
    FLEXIBLE("FLEXIBLE");

    public final String label;

    private E_AMOUNT_TYPE(String label) {
        this.label = label;
    }
}
