package com.example.manymanyUsers.statistics.controller;

import com.example.manymanyUsers.statistics.dto.SelectStatisticsResponse;
import com.example.manymanyUsers.statistics.dto.TotalStatisticsResponse;
import com.example.manymanyUsers.statistics.dto.VoteSelectResultData;
import com.example.manymanyUsers.statistics.service.StatisticsService;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "statistics", description = "통계 api")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary  = "투표 참여 인원 통계", description = "파라미터에 voteId 보내주시면 됩니다.")
    @GetMapping("/vote/{voteId}/total-statistics")
    public ResponseEntity<TotalStatisticsResponse> getTotalStatistics(@PathVariable("voteId") Long voteId) {

        Long totalVoteCount = statisticsService.getTotalStatistics(voteId);

        TotalStatisticsResponse totalStatisticsResponse = TotalStatisticsResponse.builder()
                .voteId(voteId)
                .totalVoteCount(totalVoteCount)
                .message("해당 voteId 투표 참여 인원 통계 조회에 성공했습니다")
                .build();

        return new ResponseEntity(totalStatisticsResponse, HttpStatus.OK);
    }

    @Operation(summary = "A, B 투표 참여인원, 퍼센테이지 통계", description = "파라미터에 voteId, gender, age, mbti 보내주시면 됩니다.")
    @GetMapping("/vote/{voteId}/select-statistics")
    public ResponseEntity<SelectStatisticsResponse> getSelectStatistics(@PathVariable("voteId") Long voteId, @RequestParam(required = false) Gender gender, @RequestParam(required = false) Age age, @RequestParam(required = false) MBTI mbti) {

        VoteSelectResultData voteSelectResultData  = statisticsService.getSelectedStatistics(voteId, gender, age, mbti);

        SelectStatisticsResponse selectStatisticsResponse = new SelectStatisticsResponse(voteId, voteSelectResultData);

        return new ResponseEntity(selectStatisticsResponse, HttpStatus.OK);
    }

}
