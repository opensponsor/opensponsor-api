package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opensponsor.utils.CDIGetter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class DebitCard extends EntityBase {
    @Column(length = 22)
    @Size(min = 16, max = 22)
    @Schema(description = "Debit Card No")
    public String cardNo;

    @Column(length = 16)
    @Size(min = 2, max = 16)
    @Schema(description = "legal name")
    public String legalName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Schema(description = "User country code")
    public CountryCodes countryCode;

    @Column(unique = true, length = 32)
    @Size(min = 4, max = 11)
    public String phoneNumber;

    @Column(length = 64)
    @Size(min = 2, max = 64)
    @Schema(description = "bank name")
    public String bankName;

    @OneToOne(optional = false)
    @Schema(description = "create for organization")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Organization organization;

    @ManyToOne
    @Schema(description = "create by user")
    @JsonIgnore
    public User user;
    
    @PrePersist
    protected void prePersist() {
        if(this.user == null) {
            this.user = CDIGetter.getUserRepository().authUser();
        }
    }
}
