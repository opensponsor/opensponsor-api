package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensponsor.enums.E_SEX;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.UUID;

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
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Comment("user name")
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @Username
    @NaturalId
    @Schema(description = "username", required = true)
    public String username;

    @Comment("url slug")
    @Column(length = 32, nullable = false, unique = true)
    @Size(min = 2, max = 32)
    @Schema(description = "url slug", required = true, minLength = 2, maxLength = 32)
    public String slug;

    @Comment("website")
    @Column(length = 128)
    @Size(min = 2, max = 128)
    @Schema(description = "website url", minLength = 2, maxLength = 128)
    public String website;

    @Comment("social")
    @Column(length = 128)
    @Size(min = 2, max = 128)
    @Schema(description = "social url", minLength = 2, maxLength = 128)
    public String social;

    @Comment("User avatar")
    @Column(length = 42)
    @Size(max = 42)
    @Schema(description = "avatar")
    public String avatar;

    @Comment("User sex")
    @Enumerated(EnumType.STRING)
    @Schema(description = "sex")
    public E_SEX sex;

    @Roles
    @Schema(description = "role")
    public String role;

    @Comment("password")
    @Column(length = 60, nullable = false)
    @Password
    @JsonIgnore
    @Schema(description = "password")
    public String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Schema(description = "User country code")
    public CountryCode countryCode;

    @Column(unique = true, length = 32)
    @Size(min = 4, max = 11)
    @Schema(description = "phoneNumber")
    @JsonIgnore
    public String phoneNumber;

    @Column(unique = true, length = 32)
    @Email
    @JsonIgnore
    @Size(min = 6, max = 32)
    @NaturalId
    @Schema(description = "email")
    public String email;

    @OneToOne(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @JsonIgnore
    @Schema(description = "token")
    public UserToken token = null;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    @Column(nullable = true)
    public Instant whenDeleted;
}
