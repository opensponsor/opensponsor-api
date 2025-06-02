package com.opensponsor.entitys;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "`licenses`")
@Schema
public class Licenses extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Comment("key")
    @Column(length = 64, nullable = false)
    @Size(min = 2, max = 64)
    @Schema(required = true)
    public String key;

    @Comment("name")
    @Column(length = 64, nullable = false)
    @Size(min = 2, max = 64)
    @Schema(required = true)
    public String name;

    @Comment("spdx id")
    @Column(length = 64, nullable = false)
    @Size(min = 2, max = 64)
    @Schema(required = true)
    public String spdxId;

    @Comment("github api url")
    @Column(length = 64, nullable = false)
    @Size(min = 2, max = 64)
    @Schema(required = true)
    public String url;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    @Column()
    public Instant whenDeleted;
}
