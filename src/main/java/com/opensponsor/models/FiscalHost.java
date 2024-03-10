package com.opensponsor.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

/**
 * fiscal host entity
 */
@Entity
@Table(name = "`fiscal_host`")
public class FiscalHost extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    public UUID id;

    @CreationTimestamp
    public Instant whenCreated;

    @UpdateTimestamp
    public Instant whenModified;

    public Instant whenDeleted;

    // 在实体管理器持久化操作之前执行 实际执行或级联。此调用与 持久化操作。
    @PrePersist
    public void prePersist() {
        // Authed.getAuthedUser()
        //     .ifPresent(value -> this.user = value);
    }

    // 在实体管理器删除操作之前执行 实际执行或级联。此调用与 删除操作。
    @PreRemove
    public void preRemove() {
        // System.out.println("PreRemove");
    }

    // 在实体管理器持久化操作 实际执行或级联。此调用在 数据库 INSERT。
    @PostPersist
    public void postPersist() {
        // System.out.println("PostPersist");
    }

    // 在实体管理器删除操作 实际执行或级联。此调用与 删除操作。
    @PostRemove
    public void postRemove() {
        // System.out.println("PostRemove");
    }

    // 在数据库 UPDATE 操作之前执行。
    @PreUpdate
    public void preUpdate() {
        // System.out.println("PreUpdate");
    }

    // 在数据库 UPDATE 操作之后执行。
    @PostUpdate
    public void postUpdate() {
        // System.out.println("PostUpdate");
    }

    // 在将实体加载到当前实体后执行 持久性上下文或实体已刷新。
    @PostLoad
    public void postLoad() {
        // System.out.println("PostLoad");
    }
}
