package com.opensponsor.entitys;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 *  公司的一种
 */
@Entity
@Table(name = "bank_account")
public class BankAccount extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    @Comment("back name")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String backName;

    @Comment("back name")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String BankCardNumber;

    // TODO 待定
    @Comment("back name")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String reserveYourPhone;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    @SoftDelete
    @Column(nullable = true)
    public Instant whenDeleted;
}
