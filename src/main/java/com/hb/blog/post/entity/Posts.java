package com.hb.blog.post.entity;

import com.hb.blog.common.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Posts extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column
    private String title;

    @Column
    private Long authorId;

    @OneToMany(mappedBy = )
    private List<Long> tagId = new ArrayList<>();

    @Column
    private List<Long> imageUrlId = new ArrayList<>();





}
