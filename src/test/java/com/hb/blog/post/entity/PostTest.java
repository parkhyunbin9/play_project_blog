package com.hb.blog.post.entity;

import com.hb.blog.user.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 Entity - 연관관계 편의 메서드 테스트")
class PostTest {

    @Test
    @DisplayName("게시물 관련 모든 태그들을 가져온다.")
    void getAllTagsTest() {

        // Given
        Tag testTag1 = createTag("테스트");
        Tag testTag2 = createTag("테스트2");

        Post testPost = Post.builder()
                .member(buildTestMember())
                .status(PostStatus.publish)
                .title("테스트 게시물")
                .build();

        // When
        testPost.addTag(testTag1);
        testPost.addTag(testTag2);

        //Then
        assertThat(testPost.searchAllTags())
                .containsAll(List.of(testTag1, testTag2));

    }

    @Test
    @DisplayName("글 저장시 신규 태그를 저장함")
    void addTagTest() {

        // Given
        Tag newTag = createTag("신규 태그");

        Post newPost = Post.builder()
                .member(buildTestMember())
                .status(PostStatus.publish)
                .title("테스트 게시물")
                .build();

        // When
        newPost.addTag(newTag);

        //Then
        assertThat(newPost.getPostTags().stream().map(e -> e.getTag().getName()))
                .contains(newTag.getName());

        assertThat(newPost.getPostTags().stream()
                .filter(e -> e.getTag().getName().equals(newTag.getName())))
                .map(postTag -> postTag.getTag().getUser_count())
                .first()
                .isEqualTo(1L);

    }

    @Test
    @DisplayName("글에서 태그를 삭제함")
    void removeTagTest() {

        // Given
        Tag newTag = createTag("신규 태그");
        Tag removeTag = createTag("신규 태그2");

        Post newPost = Post.builder()
                .member(buildTestMember())
                .status(PostStatus.publish)
                .title("테스트 게시물")
                .build();

        newPost.addTag(List.of(newTag, removeTag));

        long removeTagUseCount = removeTag.getUser_count();

        // When
        newPost.removeTag(removeTag);

        //Then
        assertThat(newPost.getPostTags().stream().map(e -> e.getTag().getName()))
                .doesNotContain(removeTag.getName());

        assertThat(newPost.getPostTags().stream().map(e -> e.getTag().getName()))
                .contains(newTag.getName());

        assertThat(removeTag.getUser_count()).isEqualTo(removeTagUseCount - 1);

    }

    @Test
    @DisplayName("글 저장시 이미지를 추가함")
    void addImageTest() {
        // Given
        Image testImage = Image.builder().build();

        Post newPost = Post.builder()
                .member(buildTestMember())
                .status(PostStatus.publish)
                .title("테스트 게시물")
                .build();

        // When
        newPost.addImage(testImage);

        // Then
        assertThat(newPost.getImageList()).contains(testImage);
        assertThat(testImage.getPost()).isEqualTo(newPost);

    }

    @Test
    @DisplayName("글 저장시 이미지를 제거함")
    void removeImageTest() {

        // Given
        Image testImage = Image.builder().imagePath("1").build();
        Image removeImage = Image.builder().imagePath("2").build();

        Post newPost = Post.builder()
                .member(buildTestMember())
                .status(PostStatus.publish)
                .title("테스트 게시물")
                .build();

        newPost.addImage(List.of(testImage, removeImage));

        // When
        newPost.removeImage(removeImage);

        // Then
        assertThat(newPost.getImageList()).contains(testImage);
        assertThat(newPost.getImageList()).doesNotContain(removeImage);
        assertThat(removeImage.getImageStatus()).isEqualTo(ImageStatus.delete);
        assertThat(removeImage.getPost()).isNull();
    }

    private Member buildTestMember() {
        return Member.builder()
                .nickname("테스트 유저")
                .password("123")
                .build();
    }

    private Tag createTag(String tagName) {
        return Tag.builder().name(tagName).build();
    }
}