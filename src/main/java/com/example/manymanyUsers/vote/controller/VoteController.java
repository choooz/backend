package com.example.manymanyUsers.vote.controller;

import com.example.manymanyUsers.vote.dto.CreateVoteRequest;
import com.example.manymanyUsers.vote.dto.CreateVoteResponse;
import com.example.manymanyUsers.vote.service.VoteService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/vote")
@RestController
@RequiredArgsConstructor
@Slf4j
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/createVote")
    public ResponseEntity createVote(@Valid @RequestBody CreateVoteRequest createVoteRequest) {


        try {
            voteService.createVote(createVoteRequest);
        } catch (NotFoundException e) {
            log.info("error",e);
            CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                    .message("이메일로 가입된 유저가 없습니다. 이메일을 다시 확인하세요.")
                    .build();
            return new ResponseEntity(createVoteResponse, HttpStatus.NOT_FOUND);
        }


        CreateVoteResponse createVoteResponse = CreateVoteResponse.builder()
                .message("투표 생성에 성공했습니다.")
                .build();

        return new ResponseEntity(createVoteResponse, HttpStatus.OK);
    }
}
