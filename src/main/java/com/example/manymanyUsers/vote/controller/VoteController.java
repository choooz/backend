package com.example.manymanyUsers.vote.controller;

import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.*;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.service.VoteService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/votes")
@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {
    private final VoteService voteService;

    @Operation(description = "투표 생성")
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

    @Operation(description = "투표 리스트 조회")
    @GetMapping("")
    public ResponseEntity<GetVoteListResponse> getVoteList(@RequestParam SortBy sortBy, @RequestParam int page, @RequestParam int size, @RequestParam(required = false) Category category) {
        Slice<VoteListData> voteListData = voteService.getVoteList(sortBy, page, size, category);
        GetVoteListResponse voteResponse = GetVoteListResponse.builder()
                .voteSlice(voteListData)
                .build();
        return new ResponseEntity(voteResponse, HttpStatus.OK);
    }

    @Operation(description = "투표 리스트 검색")
    @GetMapping("/search")
    public ResponseEntity<GetVoteListResponse> getVoteSearchList(@RequestParam String keyword, @RequestParam SortBy sortBy, @RequestParam int page, @RequestParam int size, @RequestParam(required = false) Category category) {
        Slice<VoteListData> voteListData = voteService.getSearchVoteList(keyword, sortBy, page, size, category);
        GetVoteListResponse voteResponse = GetVoteListResponse.builder()
                .voteSlice(voteListData)
                .build();
        return new ResponseEntity(voteResponse, HttpStatus.OK);
    }

    @Operation(description = "투표 단건 조회")
    @GetMapping("/{voteId}")
    public ResponseEntity<GetVoteResponse> getVote(@PathVariable Long voteId) {
        Vote vote = voteService.getVote(voteId);

        User writer = vote.getPostedUser(); // 투표 작성자

        GetVoteUserResponse getVoteUserResponse = GetVoteUserResponse.builder()
                .userImage(writer.getImageUrl())
                .userGender(writer.getGender())
                .userAge(writer.classifyAge(writer.getAge()))
                .userMbti(writer.getMbti())
                .nickName(writer.getNickname())
                .build();

        GetVoteResponse getVoteResponse = GetVoteResponse.builder()
                .user(getVoteUserResponse)
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


    @Operation(description = "투표 업데이트")
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

    @Operation(description = "투표 삭제")
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

    @Operation(description = "투표 참여")
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

    @Operation(description = "투표 북마크")
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

    
}
