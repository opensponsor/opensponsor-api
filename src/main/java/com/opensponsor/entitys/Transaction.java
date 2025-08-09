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
    @Schema(required = true)
    @Enumerated(EnumType.STRING)
    public E_TRANSACTION_TYPES type;
    // declare type: TransactionTypes | `${TransactionTypes}`;

    @Column(nullable = false)
    @Schema(required = true)
    @Enumerated(EnumType.STRING)
    public E_TRANSACTION_KIND kind;
    // declare kind: TransactionKind;

    @Column(nullable = false)
    @Schema(required = true)
    @NotNull
    public String description;
    // declare description: string;

    @Column(nullable = false)
    @Schema(required = true, description = "交易金额。贷方交易记录具有正值，借方交易记录具有负值。")
    @NotNull
    public BigDecimal amount;
    // declare amount: number;

    @Column(nullable = false)
    @Schema(required = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    public E_IBAN_CURRENCIES currency = E_IBAN_CURRENCIES.CNY;
    // declare currency: SupportedCurrency;

    // 财务主机支持的货币
    // declare hostCurrency: SupportedCurrency;

    // 财务主机货币汇率
    // declare hostCurrencyFxRate: number;

    // 集体货币净额
    // declare netAmountInCollectiveCurrency: number;

    // 金额以东道国货币计
    // declare amountInHostCurrency: number;

    // 财政主机费用以东道国货币计
    // declare hostFeeInHostCurrency: number | null;

    // 支付处理费用(以东道国货币计价)
    // declare paymentProcessorFeeInHostCurrency: number | null;

    // 平台费用(以东道国货币计价)
    // declare platformFeeInHostCurrency: number | null;

    // 税额
    // declare taxAmount: number | null;


    @Column(nullable = false)
    @Schema(required = true, description = "交易组ID")
    @NotNull
    @NotEmpty
    @NotBlank
    public UUID transactionGroup;
    // declare TransactionGroup: string;


    @Column(nullable = false)
    @Schema(required = true, description = "是否退款")
    public Boolean isRefund = false;
    // declare isRefund: boolean;

    @Column(nullable = false)
    @Schema(required = true, description = "是否是（支出/债务）")
    public Boolean isDebt;
    // declare isDebt: boolean;


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
