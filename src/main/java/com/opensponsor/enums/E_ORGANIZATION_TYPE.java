package com.opensponsor.enums;

public enum E_ORGANIZATION_TYPE {
    /**
     * finance host
     */
    FISCAL_HOST("FISCAL_HOST"),

    /**
     * common organization
     */
    ORGANIZATION("ORGANIZATION");

    public final String label;

    private E_ORGANIZATION_TYPE(String label) {
        this.label = label;
    }
}
