package com.opensponsor.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
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

    @Comment("username")
    @Column(unique = true, length = 32)
    @Size(min = 2, max = 32)
    public String username;

    @Column(unique = true, length = 32)
    @Size(min = 2, max = 32)
    public String nickName;

    @Comment("password")
    @Column(length = 32)
    public String password;

    @Column(unique = true, length = 32)
    @Email
    @Size(min = 6, max = 32)
    public String email;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
