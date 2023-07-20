package com.hb.blog.post.repository;

import com.hb.blog.comment.entity.Comment;
import com.hb.blog.post.entity.Image;
import com.hb.blog.post.entity.Post;
import com.hb.blog.post.entity.Tag;
import com.hb.blog.user.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("게시글 저장")
    void create_new_post() {

        //Given
        Member testMember = Member.builder()
                .nickname("테스트 유저")
                .password("123123")
                .description("나는 테스트 유저입니다.")
                .build();
        Tag testTag1 = Tag.builder().name("테스트1").build();
        Tag testTag2 = Tag.builder().name("테스트2").build();

        Comment comment1 = Comment.builder()
                .body("첫번째 테스트용 댓글입니다.")
                .member(testMember).build();

        Comment comment2 = Comment.builder()
                .body("두번째 테스트용 댓글입니다.")
                .member(testMember).build();

        Image testImage = Image.builder()
                .imagePath("abc")
                .build();


        Post testPost = Post.builder()
                .title("테스트")
                .body("테스트입니다.")
                .member(testMember)
                .build();

        testPost.addImage(testImage);
        testPost.addTag(List.of(testTag1, testTag2));

        comment1.addComment(testPost);
        comment2.addComment(testPost);

        //When
        postRepository.save(testPost);


        //Then
//        assertThat(testPost).
    }

}