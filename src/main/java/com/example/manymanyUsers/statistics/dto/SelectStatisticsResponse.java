package com.example.manymanyUsers.statistics.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SelectStatisticsResponse {

    private Long voteId;

    private int totalCountA;

    private int totalCountB;

    private int percentageA;

    private int percentageB;

    private String message;

    public SelectStatisticsResponse(Long voteId, VoteSelectResultData voteSelectResultData) {
        this.voteId = voteId;
        this.totalCountA = voteSelectResultData.getTotalCountA();
        this.totalCountB = voteSelectResultData.getTotalCountB();
        this.percentageA = voteSelectResultData.getPercentA();
        this.percentageB = voteSelectResultData.getPercentB();
        this.message = "해당 voteId 투표 a,b 퍼센테이지, 투표개수 통계 조회에 성공했습니다";
    }
}
