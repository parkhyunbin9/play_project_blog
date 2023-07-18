package com.hb.blog.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    @Id
    @Column(name = "post_tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long user_count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;



    public void removeTagFromPost(Post post) {
        post.getPostTags().remove(this);
        this.user_count = (this.getUser_count() > 0) ? user_count-- : 0;
    }

    @Builder
    public PostTag(Long user_count, Post post, Tag tag) {
        this.user_count = user_count;
        this.post = post;
        this.tag = tag;
    }
}
