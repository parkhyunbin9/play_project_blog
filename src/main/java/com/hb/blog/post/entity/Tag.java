package com.hb.blog.post.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "tags")
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;
}
