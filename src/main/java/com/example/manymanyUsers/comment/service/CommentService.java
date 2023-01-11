package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentLike;
import com.example.manymanyUsers.comment.dto.CommentCreateRequest;
import com.example.manymanyUsers.comment.dto.CommentDeleteRequest;
import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.repository.CommentLikeRepository;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;


    public void createComment(CommentCreateRequest commentCreateRequest) {
        Optional<User> byId = userRepository.findById(commentCreateRequest.getUserId());
        User user = byId.get();

        Comment comment = Comment.builder()
                .voteId(commentCreateRequest.getVoteId())
                .content(commentCreateRequest.getContent())
                .commentUser(user)
                .mbti(user.getMbti())
                .gender(user.getGender())
                .build();
        comment.setAge(comment.ClassifyAge(user.getAge()));
        commentRepository.save(comment);
    }


    public List<Comment> getComments(Long voteId ,String gender,String age,String mbti) {
        Gender getGender = null;
        if(gender != null) {
            getGender = Gender.valueOf(gender);
        }

        MBTI getMbti = null;
        if (mbti != null){
            getMbti = MBTI.valueOf(mbti);
        }
        List<Comment> comments = commentRepository.filteredComments(voteId,getGender,age,getMbti);

        return comments;
    }


    public void updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        Comment comment = byId.get();
        comment.update(commentUpdateRequest);
    }

    public void deleteComment(Long commentId, CommentDeleteRequest commentDeleteRequest) {
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
