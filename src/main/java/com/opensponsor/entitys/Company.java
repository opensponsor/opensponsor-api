package com.opensponsor.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;

/**
 * 公司的一种
 */
@Entity
@Table(name = "company")
public class Company extends EntityBase {
    @Comment("legal name")
    @Column(length = 32, nullable = false)
    @Size(max = 32)
    public String legalName;
}
