package com.opensponsor.entitys;

import com.opensponsor.enums.E_AMOUNT_TYPE;
import com.opensponsor.enums.E_TIER_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Tier extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    // public declare CollectiveId: number;
    @Schema(description = "所属组织")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    public Organization organization;

    // public declare slug: string;
    @Comment("url slug")
    @Schema(description = "url path")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String slug;

    // public declare name: string;
    @Comment("tier name")
    @Schema(description = "赞助等级名称")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String name;

    // public declare type: TierType;
    @Comment("tier type")
    @Schema(description = "赞助等级类型")
    @Column(nullable = false)
    @Enumerated
    public E_TIER_TYPE type;

    // public declare description: string;
    @Comment("description")
    @Schema(description = "赞助等级类型")
    @Column(length = 500, nullable = false, columnDefinition = "TEXT")
    @Size(max = 500)
    public String description;

    // public declare longDescription: string;
    @Comment("longDescription")
    @Schema(description = "赞助等级类型")
    @Column(columnDefinition = "TEXT")
    public String longDescription;

    // public declare useStandalonePage: boolean;
    @Comment("enable Standalone Page")
    @Schema(description = "是否使用独立页面")
    @Column()
    public boolean useStandalonePage;

    // public declare videoUrl: string;
    @Comment("video url")
    @Schema(description = "视频URL")
    @Column()
    public String videoUrl;

    // public declare button: string;
    @Comment("button text")
    @Schema(description = "按钮文字")
    @Column(length = 16)
    @Size(max = 16)
    public String button;

    // public declare amount: number;
    @Comment("amount")
    @Schema(description = "赞助金额")
    @Column(length = 10, nullable = false)
    @Size(min = 1, max = 90000)
    public Number amount;

    // public declare presets: number[];
    @Comment("presets")
    @Schema(description = "赞助金额范围")
    @Column()
    @ElementCollection
    public List<Number> presets = List.of();

    // public declare amountType: 'FIXED' | 'FLEXIBLE';
    @Comment("amount type")
    @Schema(description = "赞助金额类型")
    @Column()
    @Enumerated
    public E_AMOUNT_TYPE amountType;

    // public declare minimumAmount: number;
    @Comment("amount")
    @Schema(description = "最小金额")
    @Column(length = 10)
    @Size(min = 1, max = 90000)
    public Number minimumAmount;

    // public declare currency: SupportedCurrency;
    // public declare interval: 'month' | 'year' | 'flexible';
    // public declare maxQuantity: number;
    // public declare goal: number;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
