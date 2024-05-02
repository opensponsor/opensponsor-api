package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensponsor.enums.E_SEX;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.*;

/**
 * user entity
 */
@Entity
@Table(
    name = "`user`"
    // uniqueConstraints = @UniqueConstraint(name = "UniqueName", columnNames = {"name"})
)
@UserDefinition
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    @Comment("user name")
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @Username
    public String username;

    @Comment("legal name")
    @Column(length = 32)
    @Size(max = 32)
    public String legalName;

    @Comment("User avatar")
    @Column(length = 42)
    @Size(max = 42)
    public String avatar;

    @Comment("User sex")
    @Enumerated
    public E_SEX sex;

    @Roles
    public String role;

    @Comment("password")
    @Column(length = 60, nullable = false)
    @Password
    @JsonIgnore
    public String password;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    public CountryCodes countryCode;

    @Column(unique = true, length = 32)
    @Email
    @Size(min = 4, max = 11)
    public String phoneNumber;

    @Column(unique = true, length = 32)
    @Email
    @Size(min = 6, max = 32)
    public String email;

    @OneToOne(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    public UserToken token = null;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;

}
