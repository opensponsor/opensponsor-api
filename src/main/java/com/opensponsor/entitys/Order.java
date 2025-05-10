package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORDER_STATUS;
import com.opensponsor.enums.E_PAYMENT_METHOD;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "`order`")
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @ManyToOne
    @Schema(description = "create by user", required = true)
    public User user;

    // TODO
    // https://github.com/opencollective/opencollective-api/blob/263cee41cc4f34465eacb1b1bbe97a2a0e6a3d4f/server/lib/tax-forms/opencollective.ts#L74
    // https://github.com/opencollective/opencollective-api/blob/263cee41cc4f34465eacb1b1bbe97a2a0e6a3d4f/server/graphql/v1/mutations/orders.js#L144
    /*@ManyToOne
    @Schema(description = "used Tax")
    public OrderTax tax;*/

    @ManyToOne(optional = false)
    @Schema(description = "from by organization", required = true)
    public Organization organization;

    @Column(nullable = false)
    @Schema(description = "order name")
    public String name;

    @ManyToOne(optional = false)
    @Schema(description = "使用的货币")
    public CountryCode currency;

    @ManyToOne(optional = false)
    @Schema(description = "usage tier")
    public Tier tier;

    @Column(length = 10, nullable = false)
    @Size(min = 1, max = 10)
    @Schema(description = "total amount")
    public Number totalAmount;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @Schema(description = "description")
    public String description;

    @Column(nullable = false)
    @Schema(description = "is guest")
    public boolean isGuest = false;

    @Column(length = 10, nullable = false)
    @Size(min = 1, max = 10)
    @Schema(description = "quantity (only product)")
    public Number quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "order status")
    public E_ORDER_STATUS status;

    @Column(nullable = false)
    @Schema(description = "pay status", required = true)
    public boolean payStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "支付方式")
    public E_PAYMENT_METHOD PaymentMethod;

    @Column(length = 32, nullable = false)
    @Schema(description = "trade no", required = true)
    public String tradeNo;

    @Column(length = 32)
    @Schema(description = "out trade no")
    public String outTradeNo;

    @Column(nullable = false)
    @Schema(description = "order expires time")
    public Instant whenExpires;

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
