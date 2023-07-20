package com.hb.blog.post.repository;

import com.hb.blog.post.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;
    
    @Test
    @DisplayName("태그 저장")
    void saveTagTest() {

        // Given
        String tagName = "테스트";
        Tag testTag = Tag.builder().name(tagName).build();
        Long beforeSavedTagId = testTag.getTagId();

        // When
        Tag dbTag = tagRepository.save(testTag);

        // Then
        assertThat(beforeSavedTagId).isNull();
        assertThat(dbTag.getName()).isEqualTo(tagName);
        assertThat(dbTag.getTagId()).isNotNull();

    }

    @Test
    @DisplayName("태그 조회")
    void findTagTest() {

        // Given
        String tagName = "테스트";
        Tag testTag = Tag.builder().name(tagName).build();
        tagRepository.save(testTag);

        // When
        Optional<Tag> findTag = tagRepository.findTagByName(tagName);

        // Then
        assertThat(findTag.get().getName()).isEqualTo(tagName);

    }

    @Test
    @DisplayName("태그 이름 기준 포함 태그들 조회")
    void findTagListByNamesTest() {

        // Given
        String tagName = "테스트";
        String addWord = "abc";
        Tag testTag = Tag.builder().name(tagName).build();
        Tag testTagNamePlusWord = Tag.builder().name(tagName+addWord).build();
        Tag testTagNamePlusWord2 = Tag.builder().name(addWord+ tagName).build();
        Tag testTagNameNoContain = Tag.builder().name(addWord).build();

        tagRepository.save(testTag);
        tagRepository.save(testTagNamePlusWord);
        tagRepository.save(testTagNamePlusWord2);
        tagRepository.save(testTagNameNoContain);

        // When
        List<Tag> findTagList = tagRepository.findTagsByNameContaining(tagName);

        // Then
        assertThat(findTagList.size()).isEqualTo(3);
        assertThat(findTagList).contains(testTag, testTagNamePlusWord, testTagNamePlusWord2);
        assertThat(findTagList).doesNotContain(testTagNameNoContain);

    }

    @Test
    @DisplayName("태그 삭제")
    void deleteTagTest() {

        // Given
        String tagName = "테스트";
        Tag testTag = Tag.builder().name(tagName).build();
        tagRepository.save(testTag);

        // When
        Optional<Tag> findTag = tagRepository.findTagByName(tagName);
        assertThat(findTag.isEmpty()).isFalse();
        tagRepository.delete(testTag);
        Optional<Tag> afterDeleteTag = tagRepository.findTagByName(tagName);

        // Then
        assertThat(afterDeleteTag.isEmpty()).isTrue();

    }
}