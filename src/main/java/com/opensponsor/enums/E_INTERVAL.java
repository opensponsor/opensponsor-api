package com.opensponsor.enums;

public enum E_INTERVAL {
    ONETIME("ONETIME"),
    MONTH("MONTH"),
    YEAR("YEAR"),
    FLEXIBLE("FLEXIBLE");

    public final String label;
    private E_INTERVAL(String label) {
        this.label = label;
    }
}
