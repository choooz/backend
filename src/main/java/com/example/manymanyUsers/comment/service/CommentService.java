package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentLike;
import com.example.manymanyUsers.comment.dto.CommentCreateRequest;
import com.example.manymanyUsers.comment.dto.CommentDeleteRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.repository.CommentLikeRepository;
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
    private final CommentLikeRepository commentLikeRepository;


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
        comment.ClassifyAge(user.getAge());   //comment의 Age 정보는 user 정보와 상이하기 때문에 ClassifyAge를 사용하여 따로 저장해주었음.
        if(null != parent){
            comment.updateParent(parent);
        }
        commentRepository.save(comment);
    }


    public List<Comment> getComments(Long voteId, Gender gender, Age age, MBTI mbti) {

        List<Comment> comments = commentRepository.filteredComments(voteId,gender,age,mbti);
        System.out.println(comments);


        List<Comment> comments2 = commentRepository.findAllOrderByParentIdAscNullsFirstCommentIdAsc();
        System.out.println(comments2);


        return comments2;
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
