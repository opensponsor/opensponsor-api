package com.opensponsor.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;

/**
 *  公司的一种
 */
@Entity
@Table(name = "country_codes")
public class CountryCodes extends EntityBase {
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
}
