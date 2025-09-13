package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opensponsor.enums.E_ORGANIZATION_ROLE;
import com.opensponsor.utils.CDIGetter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "member")
@Schema
public class Member extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Schema(required = true, description = "user")
    @OneToOne(optional = false)
    public User user;

    @Schema(required = true, description = "organization")
    @ManyToOne(optional = false)
    @JsonIgnore
    public Organization organization;

    @Column(nullable = false)
    @Schema(required = true, description = "member role")
    @Enumerated(EnumType.STRING)
    public E_ORGANIZATION_ROLE role;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    public Instant whenDeleted;

    @PrePersist
    protected void prePersist() {
        if(this.user == null) {
            this.user = CDIGetter.getUserRepository().authUser();
        }
    }
}
