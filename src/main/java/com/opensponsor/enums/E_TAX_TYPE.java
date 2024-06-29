package com.opensponsor.enums;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public enum E_TAX_TYPE {

    @Schema(description = "Value Added Tax (增值税)")
    VAT("VAT"),

    @Schema(description = "Goods and Services Tax (消费税)")
    GST("GST"),

    @Schema(description = "Income Tax (个人所得税)")
    IT("IT");

    public final String label;
    private E_TAX_TYPE(String label) {
        this.label = label;
    }
}
