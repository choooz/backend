package com.example.manymanyUsers.comment.controller;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.CommentRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> commentSave(@RequestBody @Valid CommentRequest commentRequest){
        commentService.createComment(commentRequest);

        CommonResponse createVoteResponse = CommonResponse.builder()
                .message("댓글 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }

    @PatchMapping("/update/{voteId}/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable Long voteId , @PathVariable Long commentId,@Valid @RequestBody CommentRequest commentRequest){
        commentService.updateComment(voteId,commentId,commentRequest);

        CommonResponse createVoteResponse = CommonResponse.builder()
                .message("댓글 수정에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }



    @DeleteMapping("/delete/{voteId}/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable Long voteId, @PathVariable Long commentId){
        commentService.deleteComment(voteId,commentId);

        CommonResponse createVoteResponse = CommonResponse.builder()
                .message("댓글 수정에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }


}
