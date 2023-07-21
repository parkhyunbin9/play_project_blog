package com.hb.blog.post.repository;

import com.hb.blog.post.entity.Post;
import com.hb.blog.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByMember(Member member);


}
