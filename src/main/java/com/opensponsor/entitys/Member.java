package com.opensponsor.entitys;

import com.opensponsor.enums.E_ORGANIZATION_ROLE;
import com.opensponsor.utils.CDIGetter;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name = "member")
public class Member extends EntityBase {
    @Schema(required = true, description = "user")
    @OneToOne(optional = false)
    public User user;

    @Schema(required = true, description = "organization")
    @ManyToOne(optional = false)
    public Organization organization;

    @Column(nullable = false)
    @Schema(required = true, description = "member role")
    @Enumerated
    public E_ORGANIZATION_ROLE roles;



    @PrePersist
    protected void prePersist() {
        if(this.user == null) {
            this.user = CDIGetter.getUserRepository().authUser();
        }
    }
}
