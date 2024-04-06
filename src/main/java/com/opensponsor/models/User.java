package com.opensponsor.models;

import com.opensponsor.enums.E_SEX;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * user entity
 */
@Entity
@Table(name = "`user`")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    @Comment("user name")
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String name;

    @Comment("legal name")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String legalName;

    @Comment("User avatar")
    @Column(length = 42)
    @Size(max = 42)
    public String avatar;

    @Comment("User avatar")
    @Enumerated
    public E_SEX sex;

    @Comment("password")
    @Column(length = 32, nullable = false)
    public String password;

    @Column(length = 32)
    @Size(min = 2, max = 10)
    public String dialingCode;

    @Column(unique = true, length = 32)
    @Email
    @Size(min = 4, max = 11)
    public String phone;

    @Column(unique = true, length = 32)
    @Email
    @Size(min = 6, max = 32)
    public String email;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    public List<UserToken> userTokens = new ArrayList<>();

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
