package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "organization")
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
    @ElementCollection
    public Set<E_ORGANIZATION_TYPE> types = new HashSet<>();

    @Comment("tags")
    @Column()
    @ElementCollection
    public Set<String> tags = new HashSet<>();

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
