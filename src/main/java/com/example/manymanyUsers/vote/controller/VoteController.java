package com.example.manymanyUsers.vote.controller;

import com.example.manymanyUsers.vote.dto.*;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.service.VoteService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
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
    public ResponseEntity<CreateVoteResponse> createVote(@Valid @RequestBody CreateVoteRequest createVoteRequest, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        try {
            Long voteId = voteService.createVote(createVoteRequest, longId);

            CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                    .voteId(voteId)
                    .message("투표 생성에 성공했습니다.")
                    .build();

            return new ResponseEntity(createVoteResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.info("error", e);
            CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                    .message("해당 아이디를 가진 유저가 없습니다. 아이디를 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createVoteResponse, HttpStatus.NOT_FOUND);
        }
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

    @Operation(description = "투표 단건 조회")
    @GetMapping("/{voteId}")
    public ResponseEntity getVote() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(description = "투표 업데이트")
    @PatchMapping("/{voteId}")
    public ResponseEntity<CommonResponse> updateVote(@PathVariable("voteId") Long voteId, @Valid @RequestBody UpdateVoteRequest updateVoteRequest, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        try {
            voteService.updateVote(updateVoteRequest, longId, voteId);
        } catch (NotFoundException e) {
            log.info("error", e);
            CommonResponse createVoteResponse = CommonResponse.builder()
                    .message("해당 아이디를 가진 투표가 없습니다. 아이디를 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createVoteResponse, HttpStatus.NOT_FOUND);
        }

        CommonResponse updateVoteResponse = CommonResponse.builder()
                .message("투표 수정에 성공했습니다")
                .build();

        return new ResponseEntity(updateVoteResponse, HttpStatus.OK);
    }

    @Operation(description = "투표 삭제")
    @DeleteMapping("/{voteId}")
    public ResponseEntity<CommonResponse> deleteVote(@PathVariable("voteId") Long voteId, @RequestAttribute Claims claims) throws NotFoundException {

        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        try {
            voteService.deleteVote(voteId, longId);
        } catch (NotFoundException e) {
            log.info("error",e);
            CommonResponse createVoteResponse = CommonResponse.builder()
                    .message("해당 아이디를 가진 투표가 없습니다. 아이디를 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createVoteResponse, HttpStatus.NOT_FOUND);
        }

        CommonResponse updateVoteResponse = CommonResponse.builder()
                .message("투표 삭제에 성공했습니다")
                .build();

        return new ResponseEntity(updateVoteResponse, HttpStatus.OK);
    }

    @Operation(description = "투표 참여")
    @PostMapping("/do")
    public ResponseEntity doVote(DoVoteRequest doVoteRequest, @RequestAttribute Claims claims) {

        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);

        try {
            voteService.doVote(doVoteRequest.converter(longId));
        } catch (NotFoundException e) {

            CommonResponse commonResponse = CommonResponse.builder()
                    .message("투표 참여에 실패했습니다. 토큰과 투표 아이디를 다시한번 확인하세요.")
                    .build();
            return new ResponseEntity(commonResponse,HttpStatus.NOT_FOUND);
        }

        CommonResponse commonResponse = CommonResponse.builder()
                .message("투표 참여에 성공했습니다.")
                .build();

        return new ResponseEntity(HttpStatus.OK);
    }
}
