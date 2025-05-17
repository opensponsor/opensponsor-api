package com.opensponsor.entitys;

import com.opensponsor.enums.E_IBAN_CURRENCIES;
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

/**
 *  国际区号
 */
@Entity
@Getter
@Setter
@Table(name = "country_code")
public class CountryCode extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Comment("country calling code")
    @Column(length = 16, nullable = false)
    @Size(min = 2, max = 16)
    @Schema(description = "country calling code", required = true)
    public String dial;

    @Comment("ISO3166-1-Alpha-2")
    @Column(length = 16, nullable = false)
    @Size(min = 2, max = 16)
    @Schema(description = "ISO3166-1-Alpha-2", required = true)
    public String countryCode;

    @Comment("official name en")
    @Column(length = 64, nullable = false)
    @Size(min = 1, max = 64)
    @Schema(description = "official name en", required = true)
    public String officialNameEn;

    @Comment("official name cn")
    @Column(length = 64, nullable = false)
    @Size(min = 1, max = 64)
    @Schema(description = "official name cn", required = true)
    public String officialNameCn;

    @Comment("cldr display name")
    @Column(length = 32, nullable = false)
    @Size(min = 1, max = 32)
    @Schema(description = "cldr display name", required = true)
    public String cldrDisplayName;

    @Comment("languages")
    @Column(length = 16, nullable = false)
    @Size(min = 1, max = 16)
    @Schema(description = "languages", required = true)
    public String languages;

    @Comment("currency alphabetic code")
    @Column(length = 16, nullable = false)
    @Schema(description = "currency alphabetic code", required = true)
    @Enumerated(EnumType.STRING)
    public E_IBAN_CURRENCIES currencyAlphabeticCode;

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
