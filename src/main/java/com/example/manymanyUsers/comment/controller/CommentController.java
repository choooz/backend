package com.example.manymanyUsers.comment.controller;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.*;
import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.exception.comment.CommentNotFoundException;
import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(description = "댓글 생성")
    @PostMapping("/votes/{voteId}/comments")
    public ResponseEntity<CommonResponse> createComment(@PathVariable Long voteId, @RequestBody @Valid CommentCreateRequest commentCreateRequest, @RequestAttribute Claims claims) throws UserNotFoundException {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        commentService.createComment(voteId,commentCreateRequest,longId);

        CommonResponse commonResponse = CommonResponse.builder()
                .message("댓글 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(commonResponse, HttpStatus.OK);
    }


    @Operation(description = "댓글 조회")
    @GetMapping("/votes/{voteId}/comments")
    public ResponseEntity<List<CommentGetResponse>> getComment(@PathVariable Long voteId, @ModelAttribute CommentGetRequest commentGetRequest) {
        List<Comment> comments = commentService.getComments(voteId, commentGetRequest.getGender(), commentGetRequest.getAge(), commentGetRequest.getMbti(), commentGetRequest.getSize(),commentGetRequest.getPage(),commentGetRequest.getSortBy());
        List<CommentGetResponse> commentGetResponse = new ArrayList<>();
        Map<Long, CommentGetResponse> map = new HashMap<>();

        for (Comment comment : comments) {
            CommentGetResponse dto = CommentGetResponse.builder()
                    .id(comment.getId())
                    .userId(comment.getCommentUser().getId())
                    .content(comment.getContent())
                    .gender(comment.getGender())
                    .imageUrl(comment.getCommentUser().getImageUrl())
                    .age(comment.getAge())
                    .mbti(comment.getMbti())
                    .nickName(comment.getCommentUser().getNickname())
                    .createdDate(comment.getCreatedDate())
                    .likeCount(comment.getLikeCount())
                    .hateCount(comment.getHateCount())
                    .children(new ArrayList<>()) //NullPointerException 발생
                    .build();
            if (comment.getParent() != null) {
                dto.setParentId(comment.getParent().getId());
            }
            map.put(dto.getId(), dto);

            if(comment.getParent() != null) {
                map.get(comment.getParent().getId()).getChildren().add(dto);
            }

            else commentGetResponse.add(dto);
        }
        return ResponseEntity.ok().body(commentGetResponse);
    }

    @Operation(description = "맛보기 댓글 조회")
    @GetMapping("/votes/{voteId}/comments/hot")
    public ResponseEntity<List<CommentGetResponse>> getHotComment(@PathVariable Long voteId, @ModelAttribute CommentGetRequest commentGetRequest) {
        List<Comment> comments = commentService.getHotComments(voteId, commentGetRequest.getGender(), commentGetRequest.getAge(), commentGetRequest.getMbti());
        List<CommentGetResponse> commentGetResponse = new ArrayList<>();
        Map<Long, CommentGetResponse> map = new HashMap<>();

        for (Comment comment : comments) {
            CommentGetResponse dto = CommentGetResponse.builder()
                    .id(comment.getId())
                    .userId(comment.getCommentUser().getId())
                    .content(comment.getContent())
                    .gender(comment.getGender())
                    .imageUrl(comment.getCommentUser().getImageUrl())
                    .age(comment.getAge())
                    .mbti(comment.getMbti())
                    .nickName(comment.getCommentUser().getNickname())
                    .createdDate(comment.getCreatedDate())
                    .likeCount(comment.getLikeCount())
                    .hateCount(comment.getHateCount())
                    .children(new ArrayList<>()) //NullPointerException 발생
                    .build();
            if (comment.getParent() != null) {
                dto.setParentId(comment.getParent().getId());
            }
            map.put(dto.getId(), dto);

            if(comment.getParent() != null) {
                map.get(comment.getParent().getId()).getChildren().add(dto);
            }

            else commentGetResponse.add(dto);
        }
        return ResponseEntity.ok().body(commentGetResponse);
    }
    @Operation(description = "댓글 수정")
    @PatchMapping("/votes/{voteId}/comments/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable Long voteId, @PathVariable Long commentId, @Valid @RequestBody CommentUpdateRequest commentUpdateRequest, @RequestAttribute Claims claims) throws UserNotFoundException, VoteNotFoundException, CommentNotFoundException {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        commentService.updateComment(voteId,commentId,longId,commentUpdateRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 수정에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }


    @Operation(description = "댓글 삭제")
    @DeleteMapping("/votes/{voteId}/comments/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Claims claims) throws UserNotFoundException,VoteNotFoundException,CommentNotFoundException{
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        commentService.deleteComment(voteId,commentId,longId);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 삭제에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }

    @Operation(description = "댓글 좋아요")
    @PostMapping("/votes/{voteId}/comments/{commentId}/likers")
    public ResponseEntity<Map<String,Object>> likeComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Claims claims) throws UserNotFoundException {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        Long likeCount = commentService.likeComment(voteId,commentId,longId);

        Map<String,Object> result = new HashMap<>();
        result.put("message","성공 코드." );
        result.put("count", likeCount);

        return ResponseEntity.ok().body(result);
    }

    @Operation(description = "댓글 싫어요")
    @PostMapping("/votes/{voteId}/comments/{commentId}/haters")
    public ResponseEntity<Map<String,Object>> hateComment(@PathVariable Long voteId, @PathVariable Long commentId, @RequestAttribute Claims claims) throws UserNotFoundException {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        Long hateCount = commentService.hateComment(voteId,commentId,longId);

        Map<String,Object> result = new HashMap<>();
        result.put("message","성공 코드." );
        result.put("count", hateCount);

        return ResponseEntity.ok().body(result);
    }

}
