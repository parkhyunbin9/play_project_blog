package com.hb.blog.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Entity(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;

    @Column
    @NotEmpty
    private String name;

    @Column
    @PositiveOrZero
    private Long user_count;

    public void detachTag() {
        this.user_count = (this.user_count > 0) ? this.user_count-1 : 0L;
    }

    public void attachTag() {
        this.user_count++;
    }

    @Builder
    public Tag(String name, Long user_count) {
        this.name = name;
        this.user_count = 0L;
    }
}
