package com.example.manymanyUsers.vote.controller;

import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.*;
import com.example.manymanyUsers.common.dto.CommonResponse;
import com.example.manymanyUsers.vote.enums.SortBy;
import com.example.manymanyUsers.vote.service.VoteService;
import io.jsonwebtoken.Claims;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/vote")
@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/createVote")
    public ResponseEntity<CommonResponse> createVote(@Valid @RequestBody CreateVoteRequest createVoteRequest, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        try {
            voteService.createVote(createVoteRequest, longId);
        } catch (NotFoundException e) {
            log.info("error", e);
            CommonResponse createVoteResponse = CommonResponse.builder()
                    .message("해당 아이디를 가진 유저가 없습니다. 아이디를 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createVoteResponse, HttpStatus.NOT_FOUND);
        }

        CommonResponse createVoteResponse = CommonResponse.builder()
                .message("투표 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<VoteResponse> getVoteList(@RequestParam SortBy sortBy, @RequestParam int page, @RequestParam int size) {
        Slice<VoteListData> voteListData = voteService.getVoteList(sortBy, page, size);
        VoteResponse voteResponse = VoteResponse.builder()
                .voteSlice(voteListData)
                .build();
        return new ResponseEntity(voteResponse, HttpStatus.OK);
    }

    @PatchMapping("/updateVote")
    public ResponseEntity<CommonResponse> updateVote(@Valid @RequestBody UpdateVoteRequest updateVoteRequest) {

        try {
            voteService.updateVote(updateVoteRequest);
        } catch (NotFoundException e) {
            log.info("error",e);
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


}
