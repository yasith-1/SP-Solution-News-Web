package com.spsolutions.newsweb.repository;

import com.spsolutions.newsweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
