package com.opensponsor.enums;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * 交易分类
 */
@Schema
public enum E_TRANSACTION_KIND {
    /** 来自“添加资金”的交易 */
    @Schema(description = "来自“添加资金”的交易")
    ADDED_FUNDS("ADDED_FUNDS"),
    /** 从项目/活动/集体转移剩余余额的交易 **/
    @Schema(description = "从项目/活动/集体转移剩余余额的交易 ")
    BALANCE_TRANSFER("BALANCE_TRANSFER"),
    /** 来自“贡献流”的交易 */
    @Schema(description = "来自“贡献流”的交易")
    CONTRIBUTION("CONTRIBUTION"),
    /** 来自“支出流”的交易 */
    @Schema(description = "来自“支出流”的交易")
    EXPENSE("EXPENSE"),
    /** 支付给托管方的托管费 */
    @Schema(description = "支付给托管方的托管费")
    HOST_FEE("HOST_FEE"),
    /** 部分托管方费用从托管方转移到平台 */
    @Schema(description = "部分托管方费用从托管方转移到平台")
    HOST_FEE_SHARE("HOST_FEE_SHARE"),
    /** 部分托管方费用从托管方转移到平台 */
    @Schema(description = "部分托管方费用从托管方转移到平台")
    HOST_FEE_SHARE_DEBT("HOST_FEE_SHARE_DEBT"),
    /** 财务托管方提供的金额用于支付退款的支付处理费用 */
    @Schema(description = "财务托管方提供的金额用于支付退款的支付处理费用")
    PAYMENT_PROCESSOR_COVER("PAYMENT_PROCESSOR_COVER"),
    /** 财务主机支付的用于支付丢失的欺诈争议费用的金额 */
    @Schema(description = "财务主机支付的用于支付丢失的欺诈争议费用的金额")
    PAYMENT_PROCESSOR_DISPUTE_FEE("PAYMENT_PROCESSOR_DISPUTE_FEE"),
    /** 保留关键字，以备将来使用 */
    @Schema(description = "保留关键字，以备将来使用")
    PAYMENT_PROCESSOR_FEE("PAYMENT_PROCESSOR_FEE"),
    /** 保留关键字，以备将来使用 */
    @Schema(description = "保留关键字，以备将来使用")
    PLATFORM_FEE("PLATFORM_FEE"),
    /** 向 Open Collective 提供的财务捐款，已添加到另一笔捐款之上 */
    @Schema(description = "向 Open Collective 提供的财务捐款，已添加到另一笔捐款之上")
    PLATFORM_TIP("PLATFORM_TIP"),
    /** 向 Open Collective 提供的财务捐款，已添加到另一笔捐款之上 */
    @Schema(description = "向 Open Collective 提供的财务捐款，已添加到另一笔捐款之上")
    PLATFORM_TIP_DEBT("PLATFORM_TIP_DEBT"),
    /** 用于预付预算 */
    @Schema(description = "用于预付预算")
    PREPAID_PAYMENT_METHOD("PREPAID_PAYMENT_METHOD"),
    /** 用于税费 */
    @Schema(description = "用于税费")
    TAX("TAX");

    public final String label;

    private E_TRANSACTION_KIND(String label) {
        this.label = label;
    }
}
