package com.hb.blog.comment.entity;

import com.hb.blog.post.entity.CommentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("대댓글 Entity - 연관관계 편의 메서드")
class SubCommentTest {

    @Test
    @DisplayName("대댓글 추가")
    void addSubCommentTest() {

        // Given
        Comment targetComment = Comment.builder().build();
        SubComment testSubComment = SubComment.builder().body("테스트 대댓글").build();
        SubComment testSubComment2 = SubComment.builder().body("테스트 대댓글2").build();

        // When
        testSubComment.addSubComment(targetComment);
        testSubComment2.addSubComment(targetComment);

        // Then
        assertThat(targetComment.getSubComments().size()).isEqualTo(2);
        assertThat(testSubComment.getParentComment()).isEqualTo(targetComment);
        assertThat(testSubComment2.getParentComment()).isEqualTo(targetComment);

    }

    @Test
    @DisplayName("대댓글 삭제")
    void removeSubCommentTest() {

        // Given
        Comment targetComment = Comment.builder().build();
        SubComment testSubComment = SubComment.builder().body("테스트 대댓글").build();
        SubComment removeSubComment = SubComment.builder().body("제거될 대댓글").build();
        testSubComment.addSubComment(targetComment);
        removeSubComment.addSubComment(targetComment);
        assertThat(targetComment.getSubComments().size()).isEqualTo(2);

        // When
        removeSubComment.removeSubComment();

        // Then
        assertThat(targetComment.getSubComments().size()).isEqualTo(1);
        assertThat(testSubComment.getParentComment()).isEqualTo(targetComment);
        assertThat(removeSubComment.getParentComment()).isNull();
        assertThat(removeSubComment.getCommentStatus()).isEqualTo(CommentStatus.delete);

    }

    @Test
    @DisplayName("댓글 - 대댓글 - 대댓글 관계")
    void subCommentTest() {

        // Given
        String firstReply = "테스트 대댓글";
        String secondReply = "테스트 대댓글의 자식";
        List<String> replyList = List.of(firstReply, secondReply);

        Comment targetComment = Comment.builder().build();
        SubComment testSubComment = SubComment.builder().body(firstReply).build();
        SubComment testSubChildComment = SubComment.builder().body(secondReply).build();

        // When
        testSubComment.addSubComment(targetComment);
        testSubChildComment.addSubComment(targetComment);

        // Then
        assertThat(targetComment.getSubComments()).containsAll(List.of(testSubComment, testSubChildComment));

        targetComment.getSubComments().forEach(subComment -> {
            int index = targetComment.getSubComments().indexOf(subComment);
            assertThat(subComment.getBody()).isEqualTo(replyList.get(index));
        });
    }
}