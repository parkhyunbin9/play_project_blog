package com.hb.blog.factory;

import com.hb.blog.comment.entity.Comment;
import com.hb.blog.post.entity.Post;

public class CommentFactory {
    public static Comment getComment(Post post) {
        return Comment.builder()
                .member(MemberFactory.getNormalMember())
                .body("테스트용 댓글")
                .post(post)
                .m
                .build();
    }
}
