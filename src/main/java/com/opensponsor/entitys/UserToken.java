package com.opensponsor.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "user_token")
public class UserToken extends PanacheEntityBase {
    @Column(unique = true, length = 1000, nullable = false)
    public String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnore
    public User user;
}
