package com.hb.blog.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    @Builder
    public Tag(String name) {
        this.name = name;
    }
}
