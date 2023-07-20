package com.hb.blog.post.repository;

import com.hb.blog.post.entity.Image;
import com.hb.blog.post.entity.ImageStatus;
import com.hb.blog.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
//    @Rollback(value = false)
    @DisplayName("이미지 저장 성공 테스트")
    void saveImageTest() {

        //Given
        String imagePath = "http://my-blog.com/imagePath/1";
        Image notSavedImage = generateImage(imagePath);
        Post testPost = Post.builder().build();
        postRepository.save(testPost);
        testPost.addImage(notSavedImage);
        Long notSavedImageId = notSavedImage.getId();

        //When
        Image dbImage = imageRepository.save(notSavedImage);

        //Then
        assertThat(notSavedImageId).isNull();
        assertThat(dbImage.getId()).isNotNull();
        assertThat(dbImage.getImagePath()).isEqualTo(imagePath);
        assertThat(dbImage.getPost()).isEqualTo(testPost);
        assertThat(testPost.getImageList().size()).isEqualTo(1);
        assertThat(testPost.getImageList().get(0)).isEqualTo(dbImage);

    }

    @Test
    @DisplayName("이미지 조회 테스트")
    void findImageFailTest() {

        //Given
        String imagePath1 = "http://my-blog.com/imagePath/1";
        String imagePath2 = "http://my-blog.com/imagePath/2";
        Image saveImage1 = generateImage(imagePath1);
        Image saveImage2 = generateImage(imagePath2);

        Image dbImage1 = imageRepository.save(saveImage1);
        imageRepository.save(saveImage2);

        //When
        List<Image> savedImageList = imageRepository.findAll();
        Optional<Image> findImage1 = imageRepository.findById(dbImage1.getId());


        //Then
        assertThat(savedImageList.size()).isEqualTo(2);
        assertThat(findImage1.get()).isEqualTo(dbImage1);
        assertThat(findImage1.get().getImagePath()).isEqualTo(imagePath1);
    }

    @Test
    @DisplayName("이미지 수정 테스트")
    void updateImageFailTest() {

        //Given
        String imagePath = "http://my-blog.com/imagePath/1";
        String beforeUpdateTitle = "before";
        String afterUpdateTitle = "after";

        Image saveImage = generateImage(imagePath);

        Post beforePost = Post.builder().title(beforeUpdateTitle).build();
        Post afterPost = Post.builder().title(afterUpdateTitle).build();
        beforePost.addImage(saveImage);

        postRepository.save(beforePost);
        postRepository.save(afterPost);
        Image dbImage = imageRepository.save(saveImage);

        //When

        dbImage.detachPost();
        dbImage.attachToPost(afterPost);
        imageRepository.flush();

        Optional<Image> updatedImage = imageRepository.findById(dbImage.getId());


        //Then

        assertThat(updatedImage.get().getImageStatus()).isEqualTo(ImageStatus.publish);
        assertThat(updatedImage.get().getPost()).isEqualTo(afterPost);
        assertThat(updatedImage.get().getPost().getTitle()).isEqualTo(afterUpdateTitle);
        assertThat(updatedImage.get().getCreatedAt()).isBefore(updatedImage.get().getUpdatedAt());
    }

    @Test
    @DisplayName("이미지 삭제 테스트")
    void deleteImageFailTest() {

        //Given
        String imagePath = "http://my-blog.com/imagePath/1";
        Image deleteImage = generateImage(imagePath);
        imageRepository.save(deleteImage);
        Long deleteImageId = deleteImage.getId();

        //When
        imageRepository.delete(deleteImage);
        Optional<Image> findImage = imageRepository.findById(deleteImageId);

        //Then
        assertThat(findImage.isEmpty()).isTrue();

    }


    private Image generateImage(String imagePath) {
        return Image.builder()
                .imagePath(imagePath)
                .build();
    }


}