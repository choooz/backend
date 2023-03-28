package com.example.manymanyUsers.vote.controller;

import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.user.dto.GetBookmarkedResponse;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.*;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.service.VoteService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/votes")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "vote", description = "vote api")
public class VoteController {
    private final VoteService voteService;

    @Operation(summary = "투표 생성", description = "헤더에 토큰 담고, 바디에 {title, titleA, titleB, imageA, imageB, filteredGender, filteredAge, filteredMbti} json 형식으로 보내주시면 됩니다.")
    @PostMapping("")
    public ResponseEntity<CreateVoteResponse> createVote(@Valid @RequestBody CreateVoteRequest createVoteRequest, @RequestAttribute Claims claims) throws UserNotFoundException {

        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        Long voteId = voteService.createVote(createVoteRequest, longId);
        CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                .voteId(voteId)
                .message("투표 생성에 성공했습니다.")
                .build();
        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }

    @Operation(summary = "투표 리스트 조회", description = "파라미터에 sortBy, page, size, category 보내주시면 됩니다.")
    @GetMapping("")
    public ResponseEntity<GetVoteListResponse> getVoteList(@RequestParam SortBy sortBy, @RequestParam int page, @RequestParam int size, @RequestParam(required = false) Category category) {
        Slice<VoteListData> voteListData = voteService.getVoteList(sortBy, page, size, category);
        GetVoteListResponse voteResponse = GetVoteListResponse.builder()
                .voteSlice(voteListData)
                .build();
        return new ResponseEntity(voteResponse, HttpStatus.OK);
    }

    @Operation(summary = "투표 리스트 검색", description = "파라미터에 keyeword, sortBy, page, size, category 보내주시면 됩니다.")
    @GetMapping("/search")
    public ResponseEntity<GetVoteListResponse> getVoteSearchList(@RequestParam String keyword, @RequestParam SortBy sortBy, @RequestParam int page, @RequestParam int size, @RequestParam(required = false) Category category) {
        Slice<VoteListData> voteListData = voteService.getSearchVoteList(keyword, sortBy, page, size, category);
        GetVoteListResponse voteResponse = GetVoteListResponse.builder()
                .voteSlice(voteListData)
                .build();
        return new ResponseEntity(voteResponse, HttpStatus.OK);
    }

    @Operation(summary = "투표 단건 조회", description = "파라미터에 voteId 보내주시면 됩니다.")
    @GetMapping("/{voteId}")
    public ResponseEntity<GetVoteResponse> getVote(@PathVariable Long voteId) {
        Vote vote = voteService.getVote(voteId);


        GetVoteUserResponse getVoteUserResponse = GetVoteUserResponse.builder()
                .userImage(vote.getPostedUser().getImageUrl())
                .userGender(vote.getPostedUser().getGender())
                .userAge(vote.getPostedUser().classifyAge(vote.getPostedUser().getAge()))
                .userMbti(vote.getPostedUser().getMbti())
                .nickName(vote.getPostedUser().getNickname())
                .build();

        GetVoteResponse getVoteResponse = GetVoteResponse.builder()
                .writer(getVoteUserResponse)
                .voteCreatedDate(vote.getCreatedDate())
                .category(vote.getCategory())
                .title(vote.getTitle())
                .imageA(vote.getImageA())
                .imageB(vote.getImageB())
                .filteredGender(vote.getFilteredGender())
                .filteredAge(vote.getFilteredAge())
                .filteredMbti(vote.getFilteredMbti())
                .titleA(vote.getTitleA())
                .titleB(vote.getTitleB())
                .description(vote.getDetail())
                .build();

        return new ResponseEntity(getVoteResponse,HttpStatus.OK);
    }


    @Operation(summary = "투표 수정", description = "파라미터에 voteId, 바디에 {title, detail, category, titleA, titleB} json 형식으로 보내주시면 됩니다.")
    @PatchMapping("/{voteId}")
    public ResponseEntity<CommonResponse> updateVote(@PathVariable("voteId") Long voteId, @Valid @RequestBody UpdateVoteRequest updateVoteRequest, @RequestAttribute Claims claims) throws UserNotFoundException, VoteNotFoundException {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        voteService.updateVote(updateVoteRequest, longId, voteId);

        CommonResponse updateVoteResponse = CommonResponse.builder()
                .message("투표 수정에 성공했습니다")
                .build();
        return new ResponseEntity(updateVoteResponse, HttpStatus.OK);
    }

    @Operation(summary = "투표 삭제", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다")
    @DeleteMapping("/{voteId}")
    public ResponseEntity<CommonResponse> deleteVote(@PathVariable("voteId") Long voteId, @RequestAttribute Claims claims) throws UserNotFoundException {

        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        voteService.deleteVote(voteId, longId);
        CommonResponse updateVoteResponse = CommonResponse.builder()
                .message("투표 삭제에 성공했습니다")
                .build();

        return new ResponseEntity(updateVoteResponse, HttpStatus.OK);
    }

    @Operation(summary = "투표 참여", description = "헤더에 토큰담고, 파라미터에 voteId, 바디에 {choice} json 형식으로 보내주시면 됩니다.")
    @PostMapping("/{voteId}/vote")
    public ResponseEntity doVote(@RequestBody DoVoteRequest doVoteRequest, @PathVariable("voteId") Long voteId, @RequestAttribute Claims claims) {

        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        voteService.doVote(doVoteRequest.converter(longId, voteId));

        CommonResponse commonResponse = CommonResponse.builder()
                .message("투표 참여에 성공했습니다.")
                .build();

        return new ResponseEntity(commonResponse ,HttpStatus.OK);
    }

    @Operation(summary = "투표 검색어 추천", description = "파라미터에 keyword, category 보내주시면 됩니다.")
    @GetMapping("/recommend")
    public ResponseEntity recommendVote(@RequestParam String keyword, @RequestParam(required = false) Category category) {

        List<String> voteRecommendListData = voteService.getRecommendVoteList(keyword, category);

        GetVoteRecommendListResponse voteResponse = GetVoteRecommendListResponse.builder()
                .recommendKeywords(voteRecommendListData)
                .build();
        return new ResponseEntity(voteResponse, HttpStatus.OK);
    }

    @Operation(summary = "투표 북마크", description = "헤더에 토큰 담고, 파라미터에 voteId 보내주시면 됩니다.")
    @PostMapping("/{voteId}/bookmark")
    public ResponseEntity<CommonResponse> bookmarkVote(@PathVariable Long voteId, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        voteService.bookmarkVote(longId, voteId);

        CommonResponse commonResponse = CommonResponse.builder()
                .message("투표 북마크 처리를 성공했습니다.")
                .build();

        return new ResponseEntity(commonResponse,HttpStatus.OK);
    }

    @Operation(summary = "투표 참여 여부 조회", description = "파라미터에 voteId, 헤더에 userId 보내주시면 됩니다.")
    @GetMapping("/{voteId}/voted")
    public ResponseEntity<GetIsUserVotedResponse> getIsUserVoted(@PathVariable Long voteId, @RequestAttribute Long userId) {
        GetIsUserVoted userVoted = voteService.isUserVoted(voteId, userId);
        GetIsUserVotedResponse getIsUserVotedResponse = new GetIsUserVotedResponse();
        getIsUserVotedResponse.converter(userVoted);
        return new ResponseEntity(getIsUserVotedResponse,HttpStatus.OK);
    }

    @Operation(summary = "북마크 여부 조회", description = "파라미어테 voteId, 헤더에 userId 보내주시면 됩니다.")
    @GetMapping("/{voteId}/bookmark")
    public ResponseEntity checkBookmarked(@PathVariable Long voteId, @RequestAttribute Long userId){

        boolean result = voteService.checkBookmarked(userId, voteId);

        return new ResponseEntity<>(new GetBookmarkedResponse(voteService.checkBookmarked(userId, voteId)), HttpStatus.OK);
    }
}
