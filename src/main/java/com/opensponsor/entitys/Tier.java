package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opensponsor.enums.E_AMOUNT_TYPE;
import com.opensponsor.enums.E_IBAN_CURRENCIES;
import com.opensponsor.enums.E_INTERVAL;
import com.opensponsor.enums.E_TIER_TYPE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"slug", "organization_id"})
    }
)
public class Tier extends EntityBase {
    // public declare CollectiveId: number;
    @Schema(description = "所属组织", required = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Organization organization;

    // public declare slug: string;
    @Comment("url slug")
    @Schema(description = "url path", minLength = 2, maxLength = 32)
    @Column(length = 32, nullable = false, unique = true)
    @Size(min = 2, max = 32)
    public String slug;

    // public declare name: string;
    @Comment("tier name")
    @Schema(description = "捐助等级名称", minLength = 2, maxLength = 32, required = true)
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String name;

    // public declare type: TierType;
    @Comment("tier type")
    @Schema(description = "捐助等级类型")
    @Column(nullable = false)
    @Enumerated
    public E_TIER_TYPE type;

    // public declare description: string;
    @Comment("description")
    @Schema(description = "描述", maxLength = 500, required = true)
    @Column(length = 500, nullable = false, columnDefinition = "TEXT")
    @Size(max = 500)
    public String description;

    // public declare longDescription: string;
    @Comment("longDescription")
    @Schema(description = "捐助等级类型", maxLength = 1000, required = false)
    @Column(columnDefinition = "TEXT")
    public String longDescription;

    // public declare useStandalonePage: boolean;
    @Comment("enable Standalone Page")
    @Schema(description = "是否使用独立页面", required = true)
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
    @Schema(description = "捐助金额")
    @Column(length = 10, nullable = false)
    @Min(1)
    @Max(100000)
    public Number amount;

    // public declare presets: number[];
    @Comment("presets")
    @Schema(description = "捐助金额范围")
    @Column()
    @ElementCollection(fetch = FetchType.EAGER)
    public List<Number> presets = List.of();

    // public declare amountType: 'FIXED' | 'FLEXIBLE';
    @Comment("amount type")
    @Schema(description = "捐助金额类型")
    @Column()
    @Enumerated
    public E_AMOUNT_TYPE amountType;

    // public declare minimumAmount: number;
    @Comment("minimumAmount")
    @Schema(description = "最小金额")
    @Column(length = 10)
    @Min(1)
    @Max(100000)
    public Number minimumAmount;

    // public declare currency: SupportedCurrency;
    @Comment("currency")
    @Schema(description = "货币")
    @Column()
    @Enumerated
    public E_IBAN_CURRENCIES currency;

    // public declare interval: 'onetime' | 'month' | 'year' | 'flexible';
    @Comment("interval")
    @Schema(description = "捐助方式")
    @Column()
    @Enumerated
    public E_INTERVAL interval;

    // public declare maxQuantity: number;
    @Comment("max quantity")
    @Schema(description = "库存")
    @Column(length = 10)
    @Min(1)
    @Max(100000)
    public Number maxQuantity;

    // public declare goal: number;
    @Comment("goal")
    @Schema(description = "筹款目标")
    @Column(length = 10)
    public Long goal;
}
