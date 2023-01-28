package com.example.manymanyUsers.comment.repository;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentEmotion;
import com.example.manymanyUsers.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentEmotionRepository extends JpaRepository<CommentEmotion, Long> {
    Optional<CommentEmotion> findByCommentAndUser(Comment comment, User user);


}
