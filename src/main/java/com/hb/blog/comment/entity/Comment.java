package com.hb.blog.comment.entity;

import com.hb.blog.common.entity.BaseEntity;
import com.hb.blog.post.entity.CommentStatus;
import com.hb.blog.post.entity.Post;
import com.hb.blog.user.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    public void addComment(Post post) {
        this.post = post;
        post.getCommentList().add(this);
        this.commentStatus = CommentStatus.publish;
    }

    public void removeComment() {
        this.post = null;
        this.commentStatus = CommentStatus.delete;
    }

    @Builder
    public Comment(String body, Member member, Post post) {
        this.body = body;
        this.member = member;
        this.post = post;
        this.commentStatus = CommentStatus.publish;
    }
}
