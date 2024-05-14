package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "organization")
@SoftDelete(columnName = "whenDeleted")
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
    @Column(length = 32)
    @Size(max = 32)
    public String legalName;

    @Comment("url slug")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String slug;

    @Comment("introduce")
    @Column(length = 150)
    @Size(min = 2, max = 150)
    public String introduce;

    @Comment("organization types")
    @Enumerated
    @Column()
    public E_ORGANIZATION_TYPE type;

    @Comment("tags")
    @Column()
    @ElementCollection
    public Set<String> tags = new HashSet<>();

    @Comment("website")
    @Column(length = 32)
    @Size(min = 2, max = 32)
    public String website;

    @Schema(description = "捐助等级")
    @OneToMany(fetch = FetchType.EAGER)
    public List<Tier> tiers = new ArrayList<>();

    @Schema(description = "Organization Owner")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public User user;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
