package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORGANIZATION_ROLE;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "member")
public class Member extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    public UUID id;

    @Schema(required = true, description = "user")
    @OneToOne(optional = false)
    public User user;

    @Schema(required = true, description = "organization")
    @ManyToOne(optional = false)
    public Organization organization;

    @Column(nullable = false)
    @Schema(required = true, description = "member role")
    @Enumerated
    public E_ORGANIZATION_ROLE roles;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    @SoftDelete
    @Column(nullable = true)
    public Instant whenDeleted;
}
