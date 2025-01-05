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
    COMMUNITY("COMMUNITY"),

    /**
     * event
     */
    @Schema(description = "事件")
    EVENT("EVENT"),

    /**
     * organization
     */
    @Schema(description = "组织")
    ORGANIZATION("ORGANIZATION"),

    /**
     * bot
     */
    @Schema(description = "机器人")
    BOT("BOT"),

    /**
     * project
     */
    @Schema(description = "项目")
    PROJECT("PROJECT"),

    /**
     * fund
     */
    @Schema(description = "基金")
    FUND("FUND"),

    /**
     * vendor
     */
    @Schema(description = "供应商")
    VENDOR("VENDOR"),

    /**
     * user
     */
    @Schema(description = "用户")
    USER("USER");



    public final String label;

    private E_ORGANIZATION_TYPE(String label) {
        this.label = label;
    }
}
