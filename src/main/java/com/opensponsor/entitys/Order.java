package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORDER_STATUS;
import com.opensponsor.enums.E_PAYMENT_METHOD;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@FilterDefs({
    @FilterDef(name="order(=userId)", parameters = {
        @ParamDef(name="userId", type = UUID.class)
    }),
    @FilterDef(name="order(=organizationId)", parameters = {
        @ParamDef(name="organizationId", type = UUID.class)
    }),
    @FilterDef(name="order(=payStatus)", parameters = {
        @ParamDef(name="payStatus", type = Boolean.class)
    }),
    @FilterDef(name="order(=status)", parameters = {
        @ParamDef(name="status", type = String.class)
    }),
    @FilterDef(name="order(inDateRange)", parameters = {
        @ParamDef(name="startDate", type = String.class),
        @ParamDef(name="endDate", type = String.class)
    }),
})
@Filters({
    @Filter(name = "order(=userId)", condition = "user_id = :userId"),
    @Filter(name = "order(=organizationId)", condition = "organization_id = :organizationId"),
    @Filter(name = "order(=payStatus)", condition = "pay_status = :payStatus"),
    @Filter(name = "order(=status)", condition = "status = :status"),
    @Filter(name = "order(inDateRange)", condition = "when_modified >= :startDate AND when_modified <= :endDate"),
})
@Getter
@Setter
@Table(name = "`order`")
@Schema
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @ManyToOne
    @Schema(description = "donation from user", required = true)
    public User user;

    @QueryParam("userId")
    @Schema(description = "filterable")
    @Transient
    public String userId;

    @QueryParam("organizationId")
    @Schema(description = "filterable")
    @Transient
    public String organizationId;

    @QueryParam("startDate")
    @Schema(description = "filterable")
    @Transient
    public String startDate;

    @QueryParam("endDate")
    @Schema(description = "filterable")
    @Transient
    public String endDate;

    // TODO
    // https://github.com/opencollective/opencollective-api/blob/263cee41cc4f34465eacb1b1bbe97a2a0e6a3d4f/server/lib/tax-forms/opencollective.ts#L74
    // https://github.com/opencollective/opencollective-api/blob/263cee41cc4f34465eacb1b1bbe97a2a0e6a3d4f/server/graphql/v1/mutations/orders.js#L144
    /*@ManyToOne
    @Schema(description = "used Tax")
    public OrderTax tax;*/

    @ManyToOne(optional = false)
    @Schema(description = "donation to organization", required = true)
    public Organization organization;

    @ManyToOne(optional = false)
    @Schema(description = "使用的货币")
    public CountryCode currency;

    @ManyToOne(optional = false)
    @Schema(description = "usage tier")
    public Tier tier;

    @Column(precision = 10, scale = 2, nullable = false)
    @Min(1)
    @Schema(description = "total amount")
    public BigDecimal totalAmount = BigDecimal.valueOf(0);

    @Column(nullable = false)
    @Schema(description = "is guest")
    public boolean isGuest = false;

    @Column(length = 10, nullable = false)
    @Min(0)
    @Schema(description = "quantity (only product)")
    public Number quantity = 0;

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
    public E_PAYMENT_METHOD paymentMethod;

    @Column(length = 32)
    @Schema(description = "trade no", required = true)
    public String tradeNo;

    @Column(length = 32, nullable = false)
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
    public Instant whenDeleted;
}
