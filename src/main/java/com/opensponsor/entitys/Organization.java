package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "organization")
public class Organization extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Comment("Organization name")
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @Schema(description = "Organization name", required = true, minLength = 2, maxLength = 32)
    public String name;

    @Comment("legal name")
    @Column(length = 32)
    @Size(min = 2, max = 32)
    @Schema(description = "Organization legal name", minLength = 2, maxLength = 32)
    public String legalName;

    @Comment("url slug")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @Schema(description = "url slug", required = true, minLength = 2, maxLength = 32)
    public String slug;

    @Comment("introduce")
    @Column(length = 150)
    @Size(min = 2, max = 150)
    @Schema(description = "introduce", minLength = 2, maxLength = 150)
    public String introduce;

    @Comment("organization types")
    @Enumerated
    @Column()
    @Schema(description = "organization types", required = true, minLength = 2, maxLength = 150)
    public E_ORGANIZATION_TYPE type;

    @Comment("tags")
    @Column()
    @ElementCollection(fetch = FetchType.EAGER)
    @Schema(description = "organization tags", required = true)
    public Set<String> tags = new HashSet<>();

    @Comment("website")
    @Column(length = 32)
    @Size(min = 2, max = 32)
    @Schema(description = "website url", minLength = 2, maxLength = 150)
    public String website;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
    @Schema(description = "捐助等级", required = true)
    public List<Tier> tiers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Schema(description = "所属用户", required = true)
    public User user;

    @Schema(required = true, description = "members")
    @OneToMany(mappedBy = "organization")
    public Set<Member> members;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    @Column(nullable = true)
    @Schema(description = "when deleted")
    public Instant whenDeleted;
}
