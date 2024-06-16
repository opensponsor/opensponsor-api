package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORDER_STATUS;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;

@Entity
public class Order extends EntityBase {
    @ManyToOne
    @Schema(description = "from by user")
    public User user;

    @ManyToOne(optional = false)
    @Schema(description = "from by organization")
    public Organization organization;

    @Column(nullable = false)
    @Schema(description = "order name")
    public String name;

    @ManyToOne(optional = false)
    @Schema(description = "使用的货币")
    public CountryCodes currency;

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
    @Enumerated
    @Schema(description = "order status")
    public E_ORDER_STATUS status;

    @Column(nullable = false)
    @Schema(description = "order expires time")
    public Instant whenExpires;
}
