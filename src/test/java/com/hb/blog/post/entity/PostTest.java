package com.hb.blog.post.entity;

import com.hb.blog.user.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class PostTest {

    @Test
    @DisplayName("게시물 관련 모든 태그들을 가져온다.")
    void getAllTagsTest() {
        Tag testTag1 = Tag.builder().name("테스트").build();
        Tag testTag2 = Tag.builder().name("테스트2").build();

        Post testPost = Post.builder()
                .member(buildTestMember())
                .status(PostStatus.publish)
                .title("테스트 게시물")
                .build();

        testPost.addTag(testTag1);
        testPost.addTag(testTag2);
        for (Tag allTag : testPost.searchAllTags()) {
            System.out.println("Tag.getName() = " + allTag.getName());
        }
        Assertions.assertThat(testPost.searchAllTags())
                .containsAll(List.of(testTag1, testTag2));

    }

    @Test
    @DisplayName("글 저장시 신규 태그를 저장함")
    void addTagTest() {

    }

    @Test
    @DisplayName("글 저장시 이미지를 추가함")
    void addImageTest() {

    }

    @Test
    @DisplayName("글 저장시 이미지를 제거함")
    void removeImageTest() {

    }

    @Test
    @DisplayName("글 저장시 이미지를 수정함")
    void changeImageTest() {

    }

    @Test
    @DisplayName("글 저장시저장함")
    void addCommentTest() {

    }

    private Member buildTestMember() {
        return Member.builder()
                .nickname("테스트 유저")
                .password("123")
                .build();
    }
}