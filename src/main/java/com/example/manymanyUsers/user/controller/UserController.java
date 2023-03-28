package com.example.manymanyUsers.user.controller;

import com.example.manymanyUsers.comment.service.CommentService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.statistics.service.StatisticsService;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.dto.*;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.VoteType;
import com.example.manymanyUsers.vote.service.VoteService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Tag(name = "user", description = "user api")
public class UserController {
    private final UserService userService;
    private final VoteService voteService;
    private final StatisticsService statisticsService;
    private final CommentService commentService;

    @Operation(summary = "회원가입", description = "바디에 {name, email, password, provider, providerId} 을 json 형식으로 보내주시면 됩니다.")
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

    @Operation(summary = "유저 정보 추가", description = "헤더에 토큰, 바디에 {mbti, age, gender} json 행식으로 보내주시면 됩니다.")
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

    @Operation(summary = "유저 관심사 카테고리 추가", description = "헤더에 토큰, 바디에 {categoryLists} json 형식으로 보내주시면 됩니다.")
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


    @Operation(summary = "마이페이지 타입별 voteList", description = "헤더에 토큰, voteType, page, size 보내주시면 됩니다.")
    @GetMapping("/mypage")
    public ResponseEntity<Slice<MyPageResponse>> getVotesByUser(@Parameter(description = "created,participated,bookmarked", required = true) @RequestParam VoteType voteType, @RequestParam int page, @RequestParam int size, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        List<MyPageResponse> responses = new ArrayList<>();

        Slice<Vote> voteList = voteService.getVotesByUser(longId, voteType, page, size);

        boolean isLast = voteList.getSize() < size;

        for (Vote vote : voteList) {
            MyPageResponse myPageResponse = MyPageResponse.builder()
                    .voteId(vote.getId())
                    .imageA(vote.getImageA())
                    .imageB(vote.getImageB())
                    .title(vote.getTitle())
                    .countVoted(statisticsService.getTotalStatistics(vote.getId())) //총 투표인원  조회로 쿼리가 size 만큼 더 발생하는 문제
                    .countComment(commentService.getCommentsCountByVote(vote.getId())) //comment 조회로 쿼리가 size 만큼 더 발생하는 문제
                    .modifiedDate(vote.getModifiedDate())
                    .build();
            responses.add(myPageResponse);
        }

        Slice<MyPageResponse> sliceResponse = new SliceImpl<>(responses, voteList.getPageable(), isLast);


        return new ResponseEntity<>(sliceResponse, HttpStatus.OK);

    }

    @Operation(summary = "마이페이지 타입별 voteCount", description = "헤더에 토큰 보내주시면 됩니다.")
    @GetMapping("/mypage/count")
    public ResponseEntity<MyPageCountResponse> getMyPageCount(@RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        Map<String, Long> map = userService.getMyPageCount(longId);
        Long countCreatedVote = map.get("CREATED_VOTE");
        Long countParticipatedVote = map.get("PARTICIPATED_VOTE");
        Long countBookmarkedVote = map.get("BOOKMARKED_VOTE");

        MyPageCountResponse myPageCountResponse = new MyPageCountResponse(countCreatedVote, countParticipatedVote, countBookmarkedVote);


        return new ResponseEntity(myPageCountResponse, HttpStatus.OK);

    }

    @Operation(summary = "프로필 수정", description = "헤더에 토큰, 바디에 {nickname, image, mbti, categoryList} 을 json 형식으로 보내주시면 됩니다.")
    @PatchMapping("/mypage/edit")
    public ResponseEntity<GetUserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest, @RequestAttribute Claims claims) throws NotFoundException {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        User user = userService.updateUser(longId, updateUserRequest.getNickname(), updateUserRequest.getImage(), updateUserRequest.getMbti(), updateUserRequest.getCategoryList());

        GetUserResponse getUserResponse = new GetUserResponse(user.getId(), user.getImageUrl(), user.getNickname(), user.getGender(), user.getAge(), user.classifyAge(user.getAge()), user.getMbti());

        return new ResponseEntity(getUserResponse, HttpStatus.OK);
    }


}
