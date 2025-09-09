package com.opensponsor.entitys;

import com.opensponsor.enums.E_IBAN_CURRENCIES;
import com.opensponsor.enums.E_TRANSACTION_KIND;
import com.opensponsor.enums.E_TRANSACTION_TYPES;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transaction extends PanacheEntityBase {
    // declare id: CreationOptional<number>;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Column(nullable = false)
    @Schema(required = true, description = "交易类型")
    @Enumerated(EnumType.STRING)
    public E_TRANSACTION_TYPES type;

    @Column(nullable = false)
    @Schema(required = true ,description = "交易分类")
    @Enumerated(EnumType.STRING)
    public E_TRANSACTION_KIND kind;

    @Column(nullable = false)
    @Schema(required = true, description = "描述交易")
    @NotNull
    public String description;

    @Column(nullable = false)
    @Schema(required = true, description = "交易金额。贷方交易记录具有正值，借方交易记录具有负值。")
    @NotNull
    public BigDecimal amount;

    @Column(nullable = false)
    @Schema(required = true, description = "交易货币")
    @NotNull
    @Enumerated(EnumType.STRING)
    public E_IBAN_CURRENCIES currency = E_IBAN_CURRENCIES.CNY;

    @Column(nullable = false)
    @Schema(required = true, description = "财务主机支持的货币")
    @NotNull
    @Enumerated(EnumType.STRING)
    public E_IBAN_CURRENCIES hostCurrency = E_IBAN_CURRENCIES.CNY;

    // 财务主机货币汇率
    // 交易发生时汇率
    @Column(nullable = false)
    @Schema(required = true, description = "财务主机货币汇率")
    @NotNull
    public int hostCurrencyFxRate;

    // 集体货币净额
    @Column(nullable = false)
    @Schema(required = true, description = "集体货币净额（余额？）")
    @NotNull
    public int netAmountInCollectiveCurrency;

    // 金额以东道国货币计
    @Column(nullable = false)
    @Schema(required = true, description = "金额以东道国货币计")
    @NotNull
    public int amountInHostCurrency;

    // 财政主机费用以东道国货币计
    @Column(nullable = false)
    @Schema(required = true, description = "财政主机费用以东道国货币计")
    @NotNull
    public int hostFeeInHostCurrency;

    // 支付处理费用(以东道国货币计价)
    @Column(nullable = false)
    @Schema(required = true, description = "财政主机费用以东道国货币计")
    @NotNull
    public int paymentProcessorFeeInHostCurrency;

    // 平台费用(以东道国货币计价)
    @Column(nullable = false)
    @Schema(required = true, description = "平台费用(以东道国货币计价)")
    @NotNull
    public int platformFeeInHostCurrency;

    // 税额
    @Column(nullable = false)
    @Schema(required = true, description = "税额")
    @NotNull
    public int taxAmount;


    @Column(nullable = false)
    @Schema(required = true, description = "交易组ID")
    @NotNull
    @NotEmpty
    @NotBlank
    public UUID transactionGroup;


    @Column(nullable = false)
    @Schema(required = true, description = "是否退款")
    public Boolean isRefund = false;

    @Column(nullable = false)
    @Schema(required = true, description = "是否是（支出/债务）")
    public Boolean isDebt;

    // // Foreign keys
    // declare CreatedByUserId: number;
    // declare FromCollectiveId: number;
    // declare CollectiveId: number;
    // declare HostCollectiveId: number;
    // declare UsingGiftCardFromCollectiveId: number;
    // declare OrderId: number;
    // declare ExpenseId: number;
    // declare PayoutMethodId: number;
    // declare PaymentMethodId: number;
    // declare RefundTransactionId: number;

    // // Associations
    // declare createdByUser?: User;
    // declare fromCollective?: Collective;
    // declare host?: Collective;
    // declare usingGiftCardFromCollective?: Collective;
    // declare collective?: Collective;
    // declare PaymentMethod?: PaymentMethod;
    // declare PayoutMethod?: PayoutMethod;
    // declare Order?: Order;

    // // Getter Methods
    // declare info?: Partial<Transaction>;
    // declare merchantId?: string;
    // declare netAmountInHostCurrency?: number;
    // declare amountSentToHostInHostCurrency?: number;

    // // Virtual field
    // declare settlementStatus: TransactionSettlementStatus;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    @Column()
    public Instant whenDeleted;
}
