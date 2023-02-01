package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentEmotion;
import com.example.manymanyUsers.comment.dto.CommentCreateRequest;
import com.example.manymanyUsers.comment.dto.CommentDeleteRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.enums.Emotion;
import com.example.manymanyUsers.comment.repository.CommentEmotionRepository;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentEmotionRepository commentEmotionRepository;


    public void createComment(CommentCreateRequest commentCreateRequest) {
        Optional<User> byId = userRepository.findById(commentCreateRequest.getUserId());
        User user = byId.get();

        Comment parent = null;
        //대댓글 이면
        if(commentCreateRequest.getParentId() != null){
            Optional<Comment> byParentId = commentRepository.findById(commentCreateRequest.getParentId());
            parent = byParentId.get();
        }

        Comment comment = Comment.builder()
                .voteId(commentCreateRequest.getVoteId())
                .content(commentCreateRequest.getContent())
                .commentUser(user)
                .mbti(user.getMbti())
                .gender(user.getGender())
                .build();
        comment.classifyAge(user.getAge());   //comment의 Age 정보는 user 정보와 상이하기 때문에 ClassifyAge를 사용하여 따로 저장해주었음.
        if(null != parent){
            comment.updateParent(parent);
        }
        commentRepository.save(comment);
    }


    public List<Comment> getComments(Long voteId, Gender gender, Age age, MBTI mbti) {

//        List<Comment> comments = commentRepository.findCommentsByfilterAndGetChildren(voteId,gender,age,mbti);
        List<Comment> comments = commentRepository.findCommentsAllByfilter(voteId,gender,age,mbti);



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

        Optional<CommentEmotion> byCommentAndUser = commentEmotionRepository.findByCommentAndUser(comment, user);


        byCommentAndUser.ifPresentOrElse(
                commentEmotion -> {
                    //좋아요를 눌렀는데 또 눌렀을 경우 좋아요 취소
                    if (commentEmotion.getEmotion().equals(Emotion.LIKE)) {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);
                        comment.updateLikeHateCount();
                    }
                    //싫어요를 누른 상태로 좋아요를 누른 경우 싫어요 취소 후 좋아요로 등록
                    else {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);

                        CommentEmotion changeEmotion = new CommentEmotion();

                        changeEmotion.setEmotionLike();
                        changeEmotion.mappingComment(comment);
                        changeEmotion.mappingUser(user);
                        comment.updateLikeHateCount();

                        commentEmotionRepository.save(changeEmotion);
                    }

                },
                // 좋아요가 없을 경우 좋아요 추가
                () -> {
                    CommentEmotion commentEmotion = new CommentEmotion();

                    commentEmotion.setEmotionLike();
                    commentEmotion.mappingComment(comment);
                    commentEmotion.mappingUser(user);
                    comment.updateLikeHateCount();

                    commentEmotionRepository.save(commentEmotion);
                }
        );

        return comment.getLikeCount();
    }

    public Long hateComment(Long commentId, Long userId) {
        Optional<Comment> byIdComment = commentRepository.findById(commentId);
        Comment comment = byIdComment.get();

        Optional<User> byIdUser = userRepository.findById(userId);
        User user = byIdUser.get();

        Optional<CommentEmotion> byCommentAndUser = commentEmotionRepository.findByCommentAndUser(comment, user);


        byCommentAndUser.ifPresentOrElse(
                commentEmotion -> {
                    //싫어요를 눌렀는데 또 눌렀을 경우 싫어요 취소
                    if (commentEmotion.getEmotion().equals(Emotion.HATE)) {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);
                        comment.updateLikeHateCount();
                    }
                    //좋아요를 누른 상태로 싫어요를 누른 경우 좋아요 취소 후 싫어요로 등록
                    else {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);

                        CommentEmotion changeEmotion = new CommentEmotion();

                        changeEmotion.setEmotionHate();
                        changeEmotion.mappingComment(comment);
                        changeEmotion.mappingUser(user);
                        comment.updateLikeHateCount();

                        commentEmotionRepository.save(changeEmotion);
                    }

                },
                // 싫어요가 없을 경우 싫어요 추가
                () -> {
                    CommentEmotion commentEmotion = new CommentEmotion();

                    commentEmotion.setEmotionHate();
                    commentEmotion.mappingComment(comment);
                    commentEmotion.mappingUser(user);

                    comment.updateLikeHateCount();

                    commentEmotionRepository.save(commentEmotion);
                }
        );

        return comment.getHateCount();
    }



}
