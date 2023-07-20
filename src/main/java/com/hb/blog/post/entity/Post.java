package com.hb.blog.post.entity;

import com.hb.blog.comment.entity.Comment;
import com.hb.blog.common.entity.BaseEntity;
import com.hb.blog.user.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'untitled'")
    @Size(max = 50)// varchar 50 대략 한글 25~30자 내 한글 1 = 3 bytes
    private String title;

    @Column
    @Lob
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post")
    private List<Image> imageList;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Enumerated(EnumType.STRING)
    private CommentAuth commentAuth;

    @OneToMany(mappedBy = "post")
    private Set<PostTag> postTags;

    public List<Tag> searchAllTags(){
        return postTags.stream().map(postTag -> postTag.getTag()).collect(Collectors.toList());
    }

    public void addTag(Tag tag) {
        tag.attachTag();
        PostTag postTag = PostTag.builder().post(this).tag(tag).build();
        this.postTags.add(postTag);
    }

    public void addTag(List<Tag> tags) {
        tags.stream().forEach(tag -> {
            tag.attachTag();
            PostTag postTag = PostTag.builder().post(this).tag(tag).build();
            this.postTags.add(postTag);
        });
    }

    public void removeTag(Tag tag) {
        this.postTags.stream().filter(postTag ->
                postTag.getTag().getName().equals(tag.getName()))
                .findAny()
                .ifPresent(postTag -> {
                    this.postTags.remove(postTag);
                    postTag.getTag().detachTag();
                });
    }

    public void addImage(Image image) {
        this.imageList.add(image);
        image.attachToPost(this);
    }

    public void addImage(List<Image> images) {
        images.stream().forEach(image -> {
            this.imageList.add(image);
            image.attachToPost(this);
        });
    }

    public void removeImage(Image image) {
        this.imageList.remove(image);
        image.detachPost();
    }

    @Builder
    public Post(String title, String body, Member member, CommentAuth commentAuth, List<Comment> commentList, List<Image> imageList, PostStatus status, Set<PostTag> postTags) {
        this.title = title;
        this.body = body;
        this.member = member;
        this.commentList = (CollectionUtils.isEmpty(commentList)) ? new ArrayList<>() : commentList;
        this.imageList  = (CollectionUtils.isEmpty(imageList))? new ArrayList<>() : imageList;
        this.status = PostStatus.publish;
        this.commentAuth = CommentAuth.Any;
        this.postTags = (CollectionUtils.isEmpty(postTags)) ? new HashSet<>() : postTags;
    }
}
