package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opensponsor.enums.E_AMOUNT_TYPE;
import com.opensponsor.enums.E_IBAN_CURRENCIES;
import com.opensponsor.enums.E_INTERVAL;
import com.opensponsor.enums.E_TIER_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
    name = "tier",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "organization_id"}),
        @UniqueConstraint(columnNames = {"slug", "organization_id"}),
    }
)
@Schema
public class Tier extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Schema(description = "所属组织", required = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Organization organization;

    @Comment("tier name")
    @Schema(description = "捐助等级名称", minLength = 2, maxLength = 32, required = true)
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String name;

    @Comment("url slug")
    @Schema(description = "url path", minLength = 2, maxLength = 32, required = true)
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String slug;

    @Comment("tier type")
    @Schema(description = "捐助等级类型", required = true)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public E_TIER_TYPE type;

    @Comment("description")
    @Schema(description = "描述", maxLength = 500, required = true)
    @Column(length = 500, nullable = false, columnDefinition = "TEXT")
    @Size(max = 500)
    public String description;

    @Comment("longDescription")
    @Schema(description = "完整的描述", maxLength = 1000)
    @Column(columnDefinition = "TEXT")
    public String longDescription;

    @Comment("enable Standalone Page")
    @Schema(description = "是否使用独立页面", required = true)
    @Column()
    public boolean useStandalonePage = false;

    // public declare videoUrl: string;
    @Comment("video url")
    @Schema(description = "视频URL")
    @Column()
    public String videoUrl;

    // public declare button: string;
    @Comment("button text")
    @Schema(description = "按钮文字", required = true)
    @Column(length = 16)
    @Size(max = 16)
    public String button;

    // public declare amount: number;
    @Comment("amount")
    @Schema(description = "捐助金额", required = true)
    @Column(length = 10, nullable = false)
    @Min(1)
    @Max(100000)
    public Number amount;

    @Comment("presets")
    @Schema(description = "捐助金额范围", required = true)
    @Column()
    @ElementCollection(fetch = FetchType.EAGER)
    public List<Number> presets = List.of();

    @Comment("amount type")
    @Schema(description = "捐助金额类型", required = true)
    @Column()
    @Enumerated(EnumType.STRING)
    public E_AMOUNT_TYPE amountType = E_AMOUNT_TYPE.FIXED;

    // public declare minimumAmount: number;
    @Comment("minimumAmount")
    @Schema(description = "最小金额")
    @Column(length = 10)
    @Min(1)
    @Max(100000)
    public Number minimumAmount;

    @ManyToOne(optional = false)
    @Comment("currency")
    @Schema(description = "货币", required = true)
    public CountryCode currency;

    @Comment("interval")
    @Schema(description = "捐助方式", required = true)
    @Column()
    @Enumerated(EnumType.STRING)
    public E_INTERVAL interval = E_INTERVAL.MONTH;

    @Comment("max quantity")
    @Schema(description = "库存")
    @Column(length = 10)
    @Min(1)
    @Max(100000)
    public Number maxQuantity;

    @Comment("goal")
    @Schema(description = "筹款目标")
    @Column(length = 10)
    public Long goal;

    @CreationTimestamp
    @Schema(description = "when created", required = true)
    public Instant whenCreated;

    @UpdateTimestamp
    @Schema(description = "when modified", required = true)
    public Instant whenModified;

    @SoftDelete
    public Instant whenDeleted;
}
