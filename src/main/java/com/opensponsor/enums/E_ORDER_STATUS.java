package com.opensponsor.enums;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public enum E_ORDER_STATUS {
    // New default state since August 2020
    @Schema(description = "自 2020 年 8 月以来的新默认状态")
    NEW("NEW"),

    // For Strong Customer Authentication ("3D Secure")
    @Schema(description = "用于强客户身份验证（3D Secure）")
    REQUIRE_CLIENT_CONFIRMATION("REQUIRE_CLIENT_CONFIRMATION"),

    // For One Time Contributions
    // 一次性捐款
    @Schema(description = "一次性捐款")
    PAID("PAID"),

    // For One Time and Recurring Contribution
    @Schema(description = "一次性和经常性供款")
    ERROR("ERROR"),

    // For Stripe Payment Intent based Orders
    @Schema(description = "对于基于 Stripe 支付意向的订单")
    PROCESSING("PROCESSING"),

    // When a collective/host admin rejects a contribution
    @Schema(description = "当集体/主机管理员拒绝贡献时")
    REJECTED("REJECTED"),

    // This is only for "Recurring Contributions"
    // Active Recurring contribution with up to date payments
    @Schema(description = "积极定期捐款并及时付款")
    ACTIVE("ACTIVE"),

    // When it's Cancelled by contributors or automatically after X failures
    @Schema(description = "当它被贡献者取消或在 X 次失败后自动取消时")
    CANCELLED("CANCELLED"),

    // This is only for "Manual" payments
    @Schema(description = "这仅适用于“人工”付款")
    PENDING("PENDING"), // Initial state

    // When it's marked as such by Admins
    @Schema(description = "当它被管理员标记为这样时")
    EXPIRED("EXPIRED"),

    // Disputed charges from Stripe
    DISPUTED("DISPUTED"),
    REFUNDED("REFUNDED"),
    PAUSED("PAUSED"),
    // In review charges from Stripe,
    IN_REVIEW("IN_REVIEW");

    public final String label;
    private E_ORDER_STATUS(String label) {
        this.label = label;
    }
}
