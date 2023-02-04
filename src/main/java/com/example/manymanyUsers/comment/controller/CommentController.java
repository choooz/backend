package com.example.manymanyUsers.comment.controller;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.*;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import javassist.NotFoundException;
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


    @PostMapping("votes/{voteId}/comments")
    public ResponseEntity<CommonResponse> createComment(@RequestBody @Valid CommentCreateRequest commentCreateRequest){

        commentService.createComment(commentCreateRequest);

        CommonResponse commonResponse = CommonResponse.builder()
                .message("댓글 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(commonResponse, HttpStatus.OK);
    }



    @GetMapping("votes/{voteId}/comments")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable Long voteId ,@ModelAttribute CommentGetRequest commentGetRequest) {
        List<Comment> comments = commentService.getComments(voteId,commentGetRequest.getGender(),commentGetRequest.getAge(),commentGetRequest.getMbti());
        List<CommentResponse> commentResponses = new ArrayList<>();
        Map<Long,CommentResponse> map = new HashMap<>();

        for (Comment comment : comments) {
            CommentResponse dto = CommentResponse.builder()
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

            else commentResponses.add(dto);
        }
        return ResponseEntity.ok().body(commentResponses);
    }

    @GetMapping("votes/{voteId}/comments/hot")
    public ResponseEntity<List<CommentResponse>> getHotComment(@PathVariable Long voteId, @ModelAttribute CommentGetRequest commentGetRequest) {
        List<Comment> comments = commentService.getHotComments(voteId,commentGetRequest.getGender(),commentGetRequest.getAge(),commentGetRequest.getMbti());
        List<CommentResponse> commentResponses = new ArrayList<>();
        Map<Long,CommentResponse> map = new HashMap<>();

        for (Comment comment : comments) {
            CommentResponse dto = CommentResponse.builder()
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

            else commentResponses.add(dto);
        }
        return ResponseEntity.ok().body(commentResponses);
    }

    @PutMapping("vote/{voteId}/comments/{commentId}")
    public ResponseEntity<CommonResponse> updateComment(@PathVariable Long commentId,@PathVariable Long voteId, @Valid @RequestBody CommentUpdateRequest commentUpdateRequest){
        commentService.updateComment(commentId,voteId,commentUpdateRequest);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 수정에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }



    @DeleteMapping("vote/{voteId}/comments/{commentId}")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable Long commentId,@PathVariable Long voteId){
        commentService.deleteComment(commentId,voteId);

        CommonResponse commentResponse = CommonResponse.builder()
                .message("댓글 삭제에 성공했습니다.")
                .build();

        return new ResponseEntity(commentResponse, HttpStatus.OK);
    }


    @PostMapping("vote/{voteId}/comments/{commentId}/likers/{userId}")
    public ResponseEntity<Map<String,Object>> likeComment(@PathVariable Long commentId,@PathVariable Long userId) {
        Long likeCount = commentService.likeComment(commentId,userId);

        Map<String,Object> result = new HashMap<>();
        result.put("message","성공 코드." );
        result.put("count", likeCount);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("vote/{voteId}/comments/{commentId}/haters/{userId}")
    public ResponseEntity<Map<String,Object>> hateComment(@PathVariable Long commentId,@PathVariable Long userId) {
        Long hateCount = commentService.hateComment(commentId,userId);

        Map<String,Object> result = new HashMap<>();
        result.put("message","성공 코드." );
        result.put("count", hateCount);

        return ResponseEntity.ok().body(result);
    }

}
