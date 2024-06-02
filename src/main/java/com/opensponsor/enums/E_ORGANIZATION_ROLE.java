package com.opensponsor.enums;

public enum E_ORGANIZATION_ROLE {
    ADMIN("ADMIN"),
    MEMBER("MEMBER"),
    ACCOUNTANT("ACCOUNTANT");

    public final String label;

    private E_ORGANIZATION_ROLE(String label) {
        this.label = label;
    }
}
