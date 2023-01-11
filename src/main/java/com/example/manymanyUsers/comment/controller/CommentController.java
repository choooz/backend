package com.example.manymanyUsers.comment.controller;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.CommentCreateRequest;
import com.example.manymanyUsers.comment.dto.CommentDeleteRequest;
import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    @PostMapping("votes/{voteId}/comments")
    public ResponseEntity<CommonResponse> createComment(@RequestBody @Valid CommentCreateRequest commentCreateRequest){
        commentService.createComment(commentCreateRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }



    @GetMapping("votes/{voteId}/comments/")
    public ResponseEntity<Map<String,Object>> getComment(@PathVariable Long voteId) {
        List<Comment> comments = commentService.getComments(voteId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponse dto = CommentResponse.builder()
                    .id(comment.getId())
                    .userid(comment.getCommentUser().getId())
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

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest commentUpdateRequest){
        commentService.updateComment(commentId, commentUpdateRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 수정에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }



    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable Long commentId, @Valid @RequestBody CommentDeleteRequest commentDeleteRequest){
        commentService.deleteComment(commentId, commentDeleteRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 삭제에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }


    @GetMapping("/comments/{commentId}/likers/{userId}")
    public ResponseEntity<Map<String,Object>> likeComment(@PathVariable Long commentId,@PathVariable Long userId) {
        Long likeCount = commentService.likeComment(commentId,userId);

        Map<String,Object> result = new HashMap<>();
        result.put("message","성공 코드." );
        result.put("count", likeCount);

        return ResponseEntity.ok().body(result);
    }

}
