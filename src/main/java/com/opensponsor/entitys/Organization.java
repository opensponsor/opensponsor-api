package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import com.opensponsor.utils.CDIGetter;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "organization")
@FilterDefs({
    @FilterDef(name="organization(=userId)", parameters = {
        @ParamDef(name="userId", type = UUID.class)
    }),
    @FilterDef(name="organization(%name%)", parameters = {
        @ParamDef(name="name", type = String.class)
    }),
    @FilterDef(name="organization(=type)", parameters = {
        @ParamDef(name="type", type = String.class)
    }),
    @FilterDef(name="organization(!=type)", parameters = {
        @ParamDef(name="not_type", type = String.class)
    }),
})
@Filters({
    @Filter(name = "organization(=userId)", condition = "user_id = :userId"),
    @Filter(name = "organization(%name%)", condition = "name like :name"),
    @Filter(name = "organization(=type)", condition = "type = :type"),
    @Filter(name = "organization(!=type)", condition = "type != :not_type"),
})
public class Organization extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @QueryParam("name")
    @Comment("Organization name")
    @Column(unique = true, length = 32, nullable = false)
    @Size(min = 2, max = 32)
    @Schema(description = "Organization name", required = true, minLength = 2, maxLength = 32)
    public String name;

    @Comment("legal name")
    @Column(length = 32)
    @Schema(description = "Organization legal name", minLength = 2, maxLength = 32)
    public String legalName;

    @Comment("url slug")
    @Column(length = 32, nullable = false, unique = true)
    @Size(min = 2, max = 32)
    @Schema(description = "url slug", required = true, minLength = 2, maxLength = 32)
    public String slug;

    @Comment("introduce")
    @Column(length = 150)
    @Size(min = 2, max = 150)
    @Schema(description = "introduce", minLength = 2, maxLength = 150)
    public String introduce;

    @Comment("organization types")
    @Enumerated(EnumType.STRING)
    @Column()
    @Schema(description = "organization types", required = true, minLength = 2, maxLength = 150)
    public E_ORGANIZATION_TYPE type;

    @Comment("tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @Schema(description = "organization tags", required = true)
    public Set<Tags> tags = new HashSet<>();

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organization")
    @Schema(description = "捐助等级", required = true)
    public List<Tier> tiers = new ArrayList<>();

    @QueryParam("userId")
    @Schema(description = "filterable")
    @Transient
    public String userId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Schema(description = "所属用户", required = true)
    public User user;

    @Schema(required = true, description = "members")
    @OneToMany(mappedBy = "organization")
    public Set<Member> members = new HashSet<>();

    @Schema(description = "开源协议")
    @OneToOne(fetch = FetchType.LAZY)
    public Licenses licenses;

    @Column(length = 1000)
    @Size(max = 1000)
    @Schema(description = "额外的协议信息")
    public String additionalLicenses;

    @Column(length = 1000)
    @Size(max = 1000)
    @Schema(description = "项目以往历史事件")
    public String previousEvents;

    @OneToOne(mappedBy = "organization")
    @Schema(description = "receiving-money debitCard")
    public DebitCard debitCard;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    @Column()
    @Schema(description = "when deleted")
    public Instant whenDeleted;

    @PrePersist
    protected void prePersist() {
        if(this.user == null) {
            this.user = CDIGetter.getUserRepository().authUser();
        }
    }
}
