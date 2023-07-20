package com.hb.blog.comment.repository;

import com.hb.blog.comment.entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCommentRepository extends JpaRepository<SubComment, Long> {

}
