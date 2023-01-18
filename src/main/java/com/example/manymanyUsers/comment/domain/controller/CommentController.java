package com.example.manymanyUsers.comment.domain.controller;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.dto.CommentCreateRequest;
import com.example.manymanyUsers.comment.dto.CommentDeleteRequest;
import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.dto.CommentResponse;
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
    public ResponseEntity<Map<String,Object>> getComment(@PathVariable Long voteId , @RequestParam(name = "gender", required = false) Gender gender, @RequestParam(name = "age", required = false)Age age, @RequestParam(name = "mbti" , required = false) MBTI mbti) {
        Map<Long, Comment> map = commentService.getComments(voteId,gender,age,mbti);
        List<CommentResponse> commentResponses = new ArrayList<>();

//        for (Comment comment : comments) {
//            CommentResponse dto = CommentResponse.builder()
//                    .id(comment.getId())
//                    .userId(comment.getCommentUser().getId())
//                    .content(comment.getContent())
//                    .gender(comment.getGender())
//                    .imageUrl(comment.getCommentUser().getImageUrl())
//                    .age(comment.getAge())
//                    .mbti(comment.getMbti())
//                    .nickName(comment.getCommentUser().getNickname())
//                    .createdDate(comment.getCreatedDate())
//                    .likeCount(comment.getLikeCount())
//                    .build();
//            commentResponses.add(dto);
//
//        }
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
