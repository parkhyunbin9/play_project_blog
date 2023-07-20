package com.hb.blog.post.entity;

import com.hb.blog.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String imagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private ImageStatus imageStatus = ImageStatus.publish;

    public void detachPost() {
        this.post = null;
        this.imageStatus = ImageStatus.delete;
    }

    public void attachToPost(Post post) {
        this.post = post;
        this.imageStatus = ImageStatus.publish;
    }

    @Builder
    public Image(String imagePath, Post post) {
        this.imagePath = imagePath;
        this.post = post;
    }
}
