package com.opensponsor.enums;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

// Generic, membership, service, product, donation.

public enum E_TIER_TYPE {
    @Schema(description = "通用")
    GENERIC("GENERIC"),

    @Schema(description = "成员")
    MEMBERSHIP("MEMBERSHIP"),

    @Schema(description = "赞助 (礼品卡)")
    DONATION("DONATION"),

    // @Schema(description = "门票")
    // TICKET("TICKET"),

    @Schema(description = "产品 (如T恤)")
    PRODUCT("PRODUCT"),

    @Schema(description = "服务")
    SERVICE("SERVICE");

    public final String label;

    private E_TIER_TYPE(String label) {
        this.label = label;
    }
}
