package com.hb.blog.comment.entity;

import com.hb.blog.common.entity.BaseEntity;
import com.hb.blog.post.entity.CommentStatus;
import com.hb.blog.user.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "subcomment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subcomment_id")
    private Long id;

    @Lob
    @Column
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    public void addSubComment(Comment comment) {
        this.parentComment = comment;
        this.commentStatus = CommentStatus.publish;
        comment.getSubComments().add(this);
    }

    public void removeSubComment() {
        this.commentStatus = CommentStatus.delete;
        this.parentComment.getSubComments().remove(this);
        this.parentComment = null;
    }

    @Builder
    public SubComment(String body, Member member, Comment parentComment, CommentStatus commentStatus) {
        this.body = body;
        this.member = member;
        this.parentComment = parentComment;
        this.commentStatus = CommentStatus.publish;
    }
}
