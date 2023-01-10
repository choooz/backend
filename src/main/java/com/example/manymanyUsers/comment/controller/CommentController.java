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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createComment(@RequestBody @Valid CommentRequest commentRequest){
        commentService.createComment(commentRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }



    @GetMapping("/list/{voteId}")
    public ResponseEntity<Map<String,Object>> getComment(@PathVariable Long voteId) {
        List<Comment> comments = commentService.getComments(voteId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponse dto = CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .Gender(comment.getGender())
                    .imageUrl(comment.getCommentUser().getImageUrl())
                    .Age(comment.getAge())
                    .Mbti(comment.getMbti())
                    .nickName(comment.getCommentUser().getNickname())
                    .createdDate(comment.getCreatedDate())
                    .likeCount(comment.getLikeCount())
                    .build();
            commentResponses.add(dto);

        }
        Map<String,Object> result = new HashMap<>();
        result.put("data", commentResponses);
        result.put("count", commentResponses.size());

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/update/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable Long commentId,@Valid @RequestBody CommentRequest commentRequest){
        commentService.updateComment(commentId,commentRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 수정에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }



    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable Long voteId, @PathVariable Long commentId){
        commentService.deleteComment(commentId);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 삭제에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }


    @GetMapping("like/{commentId}/{userId}")
    public ResponseEntity<Map<String,Object>> likeComment(@PathVariable Long commentId,@PathVariable Long userId) {
        Long likeCount = commentService.likeComment(commentId,userId);

        Map<String,Object> result = new HashMap<>();
        result.put("message","성공 코드." );
        result.put("count", likeCount);

        return ResponseEntity.ok().body(result);
    }

}
