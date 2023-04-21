package com.hb.blog.post.entity;

import com.hb.blog.common.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "posts")
public class Post extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(columnDefinition = "default untitle")
    @Size(max = 50)// varchar 50 대략 한글 25~30자 내 한글 1 = 3bytes
    private String title;

    @Column
    @Lob
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "tag")
    private List<Tag> tagId = new ArrayList<>();

    @OneToMany(mappedBy = "image_id")
    private List<Image> imageId = new ArrayList<>();

}
