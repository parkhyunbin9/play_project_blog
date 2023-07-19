package com.hb.blog.comment.entity;

import com.hb.blog.post.entity.CommentStatus;
import com.hb.blog.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("댓글 Entity - 연관관계 편의 메서드 테스트")
class CommentTest {

    @Test
    @DisplayName("게시글에 댓글 추가")
    void addCommentTest() {

        // Given
        Post targetPost = Post.builder().build();
        Comment testComment = Comment.builder().post(targetPost).body("테스트 댓글").build();

        // When
        testComment.addComment(targetPost);

        // Then
        assertThat(testComment.getPost()).isEqualTo(targetPost);
        assertThat(targetPost.getCommentList()).contains(testComment);
        assertThat(targetPost.getCommentList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글에 댓글 제거")
    void removeCommentTest() {

        // Given
        Post targetPost = Post.builder().build();
        Comment testComment = Comment.builder().post(targetPost).body("테스트 댓글").build();
        Comment removeComment = Comment.builder().post(targetPost).body("테스트 댓글2").build();

        // When
        testComment.addComment(targetPost);
        removeComment.addComment(targetPost);

        assertThat(targetPost.getCommentList().size()).isEqualTo(2);

        removeComment.removeComment();

        // Then
        assertThat(targetPost.getCommentList().size()).isEqualTo(1);
        assertThat(removeComment.getCommentStatus()).isEqualTo(CommentStatus.delete);
        assertThat(removeComment.getPost()).isNull();
        assertThat(targetPost.getCommentList()).contains(testComment);
    }

}