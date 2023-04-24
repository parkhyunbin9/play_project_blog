package com.hb.blog.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostTag {

    @Id
    @Column(name = "post_tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long user_count;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void addTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
        post.getPostTagsList().add(this);
    }

}
