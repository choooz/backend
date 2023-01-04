package com.example.manymanyUsers.comment.controller;


import com.example.manymanyUsers.comment.dto.CommentRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/createComment")
    public ResponseEntity<CommonResponse> commentSave(@RequestBody @Valid CommentRequest commentRequest){
        try {
            commentService.createComment(commentRequest);

        } catch (NotFoundException e) {
            log.info("error", e);
            CommonResponse createCommentResponse = CommonResponse.builder()
                    .message("해당 아이디를 가진 유저가 없습니다. 아이디를 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createCommentResponse, HttpStatus.NOT_FOUND);
        }

        CommonResponse createVoteResponse = CommonResponse.builder()
                .message("댓글 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }

}
