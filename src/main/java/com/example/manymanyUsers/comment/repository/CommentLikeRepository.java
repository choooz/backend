package com.example.manymanyUsers.comment.repository;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentLike;
import com.example.manymanyUsers.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;


public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
