package com.opensponsor.enums;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * 交易类型
 */
@Schema
public enum E_TRANSACTION_TYPES {
    /*资金流入*/
    @Schema(description = "资金流入")
    CREDIT("CREDIT"),

    /*资金流出*/
    @Schema(description = "资金流出")
    DEBIT("DEBIT");

    public final String label;

    private E_TRANSACTION_TYPES(String label) {
        this.label = label;
    }
}
