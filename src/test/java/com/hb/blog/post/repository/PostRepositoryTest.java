package com.hb.blog.post.repository;

import com.hb.blog.comment.entity.Comment;
import com.hb.blog.post.entity.*;
import com.hb.blog.user.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("새 Post 저장")
    void create_new_post() {
       //Given
        generatePost();
       
       //When 
       
       //Then        
        
    }

    private Post generatePost() {
        Member testMember = Member.builder()
                .nickname("테스트 유저")
                .password("123123")
                .description("나는 테스트 유저입니다.")
                .build();
        Tag testTag1 = Tag.builder().name("테스트1").build();
        Tag testTag2 = Tag.builder().name("테스트2").build();

        Comment.builder()
                .body("테스트용 댓글입니다.")
                .member(testMember);


        PostTag postTag = PostTag.builder()
                .tag(testTag1)

                .build();

        Image testImage = Image.builder()
                .imagePath("abc")
                .build();

        return Post.builder()
                .title("테스트")
                .body("테스트입니다.")
                .member(testMember)
//                .postTags((postTag))
                .status(PostStatus.draft)
                .imageList(List.of(testImage))
                .commentList(new ArrayList<>())
                .build();
    }

}