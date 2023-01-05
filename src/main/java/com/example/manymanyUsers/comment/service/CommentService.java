package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.CommentRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
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


    public void createComment(CommentRequest commentRequest) {
        Optional<User> byId = userRepository.findById(commentRequest.getUserId());
        User user = byId.get();

        Comment comment = Comment.builder()
                .voteId(commentRequest.getVoteId())
                .content(commentRequest.getContent())
                .commentUser(user)
                .mbti(user.getMbti())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .build();
        comment.setAge(comment.ClassifyAge(user.getAge()));
        commentRepository.save(comment);
    }


    public void getComments(Long voteId) {

    }


    public void updateComment(Long voteId, Long commentId ,CommentRequest commentRequest) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        Comment comment = byId.get();
        comment.update(commentRequest);
    }

    public void deleteComment(Long voteId,Long commentId){

        commentRepository.deleteById(commentId);
    }
}
