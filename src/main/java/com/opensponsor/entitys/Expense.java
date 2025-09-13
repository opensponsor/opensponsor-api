package com.opensponsor.entitys;

import com.opensponsor.enums.E_IBAN_CURRENCIES;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * 财务托管方可以设置多个费用，针对不同的货币
 * 收取费用会按照不同的货币收取不同的费用
 */
@Getter
@Setter
@Schema
public class Expense extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @OneToOne(optional = false)
    @Schema(required = true, description = "创建用户")
    public User user;

    @OneToOne(optional = false)
    @Schema(required = true, description = "筹款组织")
    public Organization organization;

    @Column
    @Schema(required = true, description = "费用")
    public int expense;

    @Column(nullable = false)
    @Schema(required = true, description = "交易货币")
    @NotNull
    @Enumerated(EnumType.STRING)
    public E_IBAN_CURRENCIES currency = E_IBAN_CURRENCIES.CNY;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    public Instant whenDeleted;
}
