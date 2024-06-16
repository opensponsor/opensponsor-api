package com.opensponsor.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;

/**
 * fiscal host entity, 公司的一种
 */
@Entity
@Table(name = "`fiscal_host`")
public class FiscalHost extends EntityBase {
    @Comment("legal name")
    @Column(length = 32, nullable = false)
    @Size(min = 2, max = 32)
    public String legalName;
}
