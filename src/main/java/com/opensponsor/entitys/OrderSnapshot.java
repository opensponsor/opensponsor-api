package com.opensponsor.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class OrderSnapshot extends EntityBase {
    @OneToOne
    public Order order;

    @Column
    public String string;
}
