package com.hb.blog.post.entity;

import com.hb.blog.comment.entity.Comment;
import com.hb.blog.common.entity.BaseEntity;
import com.hb.blog.user.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(columnDefinition = "default untitle")
    @Size(max = 50)// varchar 50 대략 한글 25~30자 내 한글 1 = 3bytes
    private String title;

    @Column
    @Lob
    private String body;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Image> imageList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(mappedBy = "post")
    private List<PostTag> postTagsList = new ArrayList<>();

    public List<Tag> searchAllTags(){
        return postTagsList.stream().map(postTag -> postTag.getTag()).collect(Collectors.toList());
    }

    public void addTag(Tag tag) {
        PostTag postTag = PostTag.builder().post(this).tag(tag).user_count(1L).build();
        this.postTagsList.add(postTag);
    }

    public void addImage(Image image) {
        this.imageList.add(image);
        image.attachToPost(this);
    }


    @Builder
    public Post(String title, String body, Member member, List<Comment> commentList, List<Image> imageList, PostStatus status, List<PostTag> postTagsList) {
        this.title = title;
        this.body = body;
        this.member = member;
        this.commentList = commentList;
        this.imageList = imageList;
        this.status = status;
        this.postTagsList = postTagsList;
    }
}
