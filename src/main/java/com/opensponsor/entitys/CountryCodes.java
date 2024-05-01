package com.opensponsor.entitys;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *  公司的一种
 */
@Entity
@Table(name = "country_codes")
public class CountryCodes extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    @Comment("country calling code")
    @Column(length = 16, nullable = false)
    @Size(min = 2, max = 16)
    public String dial;

    @Comment("ISO3166-1-Alpha-2")
    @Column(length = 16, nullable = false)
    @Size(min = 2, max = 16)
    public String countryCode;

    @Comment("official name en")
    @Column(length = 64, nullable = false)
    @Size(min = 1, max = 64)
    public String officialNameEn;

    @Comment("official name cn")
    @Column(length = 64, nullable = false)
    @Size(min = 1, max = 64)
    public String officialNameCn;

    @Comment("cldr display name")
    @Column(length = 32, nullable = false)
    @Size(min = 1, max = 32)
    public String cldrDisplayName;

    @Comment("languages")
    @Column(length = 16, nullable = false)
    @Size(min = 1, max = 16)
    public String languages;

    @Comment("currency alphabetic code")
    @Column(length = 16, nullable = false)
    @Size(min = 1, max = 16)
    public String currencyAlphabeticCode;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;
}
