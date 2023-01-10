package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentLike;
import com.example.manymanyUsers.comment.dto.CommentRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
import com.example.manymanyUsers.comment.repository.CommentLikeRepository;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final CommentLikeRepository commentLikeRepository;


    public void createComment(CommentRequest commentRequest) {
        Optional<User> byId = userRepository.findById(commentRequest.getUserId());
        User user = byId.get();

        Comment comment = Comment.builder()
                .voteId(commentRequest.getVoteId())
                .content(commentRequest.getContent())
                .commentUser(user)
                .mbti(user.getMbti())
                .gender(user.getGender())
                .build();
        comment.setAge(comment.ClassifyAge(user.getAge()));
        commentRepository.save(comment);
    }


    public List<Comment> getComments(Long voteId) {
        List<Comment> comments = commentRepository.findAllByVoteId(voteId);

        return comments;
    }


    public void updateComment(Long commentId, CommentRequest commentRequest) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        Comment comment = byId.get();
        comment.update(commentRequest);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    public Long likeComment(Long commentId, Long userId) {
        Optional<Comment> byIdComment = commentRepository.findById(commentId);
        Comment comment = byIdComment.get();

        Optional<User> byIdUser = userRepository.findById(userId);
        User user = byIdUser.get();

        Optional<CommentLike> byCommentAndUser = commentLikeRepository.findByCommentAndUser(comment, user);


        byCommentAndUser.ifPresentOrElse(
                commentLike -> {
                    commentLikeRepository.delete(commentLike);
                    comment.discountLike(commentLike);
                    comment.updateLikeCount();

                },
                // 좋아요가 없을 경우 좋아요 추가
                () -> {
                    CommentLike commentLike = CommentLike.builder().build();

                    commentLike.mappingPost(comment);
                    commentLike.mappingUser(user);
                    comment.updateLikeCount();

                    commentLikeRepository.save(commentLike);
                }
        );

        return comment.getLikeCount();
    }
}
