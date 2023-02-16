package com.example.manymanyUsers.user.controller;

import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.statistics.service.StatisticsService;
import com.example.manymanyUsers.user.dto.*;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.service.VoteService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final VoteService voteService;
    private final StatisticsService statisticsService;

    private final CommentService commentService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequestDto) {
        try {
            userService.registerUser(signUpRequestDto);
        } catch (Exception e) {
            CommonResponse response = new CommonResponse("조건을 만족하는 유저가 이미 존재합니다. 다시한번 확인하세요");
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }

        CommonResponse response = new CommonResponse("회원가입에 성공했습니다.");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(description = "유저 정보 추가, 헤더에 토큰담고 바디에 AddInfoRequest 행태로 넣어주셔야 합나디.")
    @PatchMapping("/addInfo")
    public ResponseEntity<CommonResponse> addUserInfo(@Valid @RequestBody AddInfoRequest addInfoRequest, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        try {
             userService.addUserInfo(addInfoRequest, longId);
        } catch (NotFoundException e) {
            CommonResponse response = new CommonResponse("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        CommonResponse response = new CommonResponse("유저 정보 추가에 성공했습니다.");
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PatchMapping("/addInterestCategory")
    public ResponseEntity<CommonResponse> AddInterestCategory(@RequestBody AddInterestCategoryRequest addInterestCategoryRequest, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        try {
            userService.addInterestCategory(addInterestCategoryRequest.getCategoryLists(), longId);
        } catch (NotFoundException e) {
            CommonResponse response = new CommonResponse("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        CommonResponse response = new CommonResponse("유저 관심사 카테고리 추가에 성공했습니다.");
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @GetMapping("/mypage")
    public ResponseEntity<List<MyPageResponse>> getVotesByUser(@RequestParam(defaultValue = "created") String type, @RequestAttribute Claims claims){
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        List<MyPageResponse> responses = new ArrayList<>();

        List<Vote> voteList = voteService.getVotesByUser(longId,type);

        for(Vote vote : voteList){
            MyPageResponse myPageResponse = MyPageResponse.builder()
                    .imageA(vote.getImageA())
                    .imageB(vote.getImageB())
                    .title(vote.getTotalTitle())
                    .countChoice(statisticsService.getTotalStatistics(vote.getId()))
                    .countComment(commentService.getCommentsCountByVote(vote.getId()))
                    .createdDate(vote.getCreatedDate())
                    .build();
            responses.add(myPageResponse);
        }

        return new ResponseEntity(responses,HttpStatus.OK);

    }

}
