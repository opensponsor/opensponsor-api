package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORDER_STATUS;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "`table`")
public class Order extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    public UUID id;

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

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    @SoftDelete
    @Column(nullable = true)
    public Instant whenDeleted;
}
