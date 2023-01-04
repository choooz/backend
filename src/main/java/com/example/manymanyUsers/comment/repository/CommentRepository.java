package com.example.manymanyUsers.comment.repository;


import com.example.manymanyUsers.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
