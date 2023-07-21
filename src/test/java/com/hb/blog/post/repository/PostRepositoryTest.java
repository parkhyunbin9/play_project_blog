package com.hb.blog.post.repository;

import com.hb.blog.comment.entity.Comment;
import com.hb.blog.comment.repository.CommentRepository;
import com.hb.blog.post.entity.Image;
import com.hb.blog.post.entity.Post;
import com.hb.blog.post.entity.Tag;
import com.hb.blog.user.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;


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
        Comment dbComment1 = commentRepository.save(comment1);
        Comment dbComment2 = commentRepository.save(comment2);
        Image dbImage = imageRepository.save(testImage);
        Tag dbTag1 = tagRepository.save(testTag1);
        Tag dbTag2 = tagRepository.save(testTag2);
        Post dbPost = postRepository.save(testPost);


        //Then
        assertThat(testPost).isEqualTo(dbPost);

        assertThat(dbPost.getImageList().size()).isEqualTo(1);
        assertThat(dbPost.getImageList().get(0)).isEqualTo(dbImage);
        assertThat(dbImage.getPost()).isEqualTo(dbPost);

        assertThat(dbPost.getCommentList().size()).isEqualTo(2);
        assertThat(dbPost.getCommentList()).contains(dbComment1, dbComment1);
        assertThat(dbComment1.getPost()).isEqualTo(dbPost);
        assertThat(dbComment2.getPost()).isEqualTo(dbPost);

        assertThat(dbTag1.getUser_count()).isEqualTo(1);
        assertThat(dbTag2.getUser_count()).isEqualTo(1);
        dbPost.getPostTags().forEach(e -> {
            assertThat(e.getTag()).isIn(dbTag1, dbTag2);
        });

    }

    @DisplayName("게시글이 제거되면 연관된 댓글, 이미지, 포스트-태그 테이블이 같이 제거 되야한다.")
    @Test
    void delete_test() {


    }
}