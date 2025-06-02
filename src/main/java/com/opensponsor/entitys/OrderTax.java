package com.opensponsor.entitys;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_tax")
@Schema
public class OrderTax extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    @Schema(required = true)
    public UUID id;

    @Column(nullable = false)
    @Schema(description = "税率比例", required = true)
    public Float percentage;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Schema(description = "纳税国家")
    public CountryCode taxedCountry;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Schema(description = "纳税人所在国家")
    public CountryCode taxerCountry;

//    taxIDNumber: string; // 纳税识别号
//    taxIDNumberFrom: string;

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
