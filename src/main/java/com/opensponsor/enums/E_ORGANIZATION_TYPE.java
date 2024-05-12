package com.opensponsor.enums;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public enum E_ORGANIZATION_TYPE {
    /**
     * finance host
     */
    @Schema(description = "财务托管方")
    FISCAL_HOST("FISCAL_HOST"),

    /**
     * common organization
     */
    @Schema(description = "通用社区")
    COMMUNITY("COMMUNITY");

    public final String label;

    private E_ORGANIZATION_TYPE(String label) {
        this.label = label;
    }
}
