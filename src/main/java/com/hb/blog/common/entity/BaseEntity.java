package com.hb.blog.common.entity;


import com.hb.blog.user.entity.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updatedAt;

    protected BaseEntity() {
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }


}
