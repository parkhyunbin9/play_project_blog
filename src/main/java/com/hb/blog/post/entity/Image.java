package com.hb.blog.post.entity;

import com.hb.blog.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column
    private byte url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void attachToPost(Post post) {
        this.post = post;
    }

    @Builder
    public Image(byte url, Post post) {
        this.url = url;
        this.post = post;
    }
}
