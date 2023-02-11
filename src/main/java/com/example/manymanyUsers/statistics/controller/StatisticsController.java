package com.example.manymanyUsers.statistics.controller;

import com.example.manymanyUsers.statistics.dto.SelectStatisticsResponse;
import com.example.manymanyUsers.statistics.dto.TotalStatisticsResponse;
import com.example.manymanyUsers.statistics.dto.VoteSelectResultData;
import com.example.manymanyUsers.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(description = "투표 참여 인원 통계")
    @GetMapping("/vote/{voteId}/total-statistics")
    public ResponseEntity<TotalStatisticsResponse> getTotalStatistics(@PathVariable("voteId") Long voteId) {

        Long totalVoteNumber = statisticsService.getTotalStatistics(voteId);

        TotalStatisticsResponse wholeStatisticsResponse = TotalStatisticsResponse.builder()
                .voteId(voteId)
                .totalVote(totalVoteNumber)
                .message("해당 voteId 투표 참여 인원 통계 조회에 성공했습니다")
                .build();

        return new ResponseEntity(wholeStatisticsResponse, HttpStatus.OK);
    }

    @Operation(description = "A, B 투표 참여 인원, 퍼센테이지 통계")
    @GetMapping("/vote/{voteId}/select-statistics")
    public ResponseEntity<SelectStatisticsResponse> getSelectStatistics(@PathVariable("voteId") Long voteId) {

        VoteSelectResultData voteSelectResultData  = statisticsService.getSelectStatistics(voteId);

        SelectStatisticsResponse selectStatisticsResponse = new SelectStatisticsResponse(voteId, voteSelectResultData);

        return new ResponseEntity(selectStatisticsResponse, HttpStatus.OK);
    }




}
