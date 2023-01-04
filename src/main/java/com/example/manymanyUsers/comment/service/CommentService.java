package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.CommentRequest;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public void createComment(CommentRequest commentRequest) throws NotFoundException {
        Optional<User> byId = userRepository.findById(commentRequest.getUserId());
        if(byId.isEmpty()){
            throw new NotFoundException("해당 아이디를 가진 유저가 없습니다. 아이디 값을 다시 한번 확인하세요.");
        }
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
}
