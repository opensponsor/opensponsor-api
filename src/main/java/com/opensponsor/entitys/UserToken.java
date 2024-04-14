package com.opensponsor.entitys;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_token")
public class UserToken extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    @Column(unique = true, length = 1000, nullable = false)
    public String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
