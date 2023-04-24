package com.hb.blog.post.entity;

import com.hb.blog.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "images")
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

    public void addImage(Post post) {
        this.post = post;
        if(post != null) post.getImageList().add(this);
    }

}
