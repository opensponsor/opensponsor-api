package com.opensponsor.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

public class Organization extends PanacheEntityBase {
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

    @Comment("slug")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String slug;

    @Comment("slug")
    @Column(length = 150)
    @Size(min = 2, max = 150)
    public String introduce;

    @Comment("website")
    @Column(length = 32)
    @Size(min = 2, max = 32)
    public String website;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
