package com.example.manymanyUsers.statistics.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalStatisticsResponse {

    private Long voteId;
    private int totalVote;
    private int aCount;
    private int aPercentage;
    private int bCount;
    private int bPercentage;
    private String message;

    @Builder
    public TotalStatisticsResponse(Long voteId, int totalVote, int aCount, int aPercentage, int bCount, int bPercentage, String message) {
        this.voteId = voteId;
        this.totalVote = totalVote;
        this.aCount = aCount;
        this.aPercentage = aPercentage;
        this.bCount = bCount;
        this.bPercentage = bPercentage;
        this.message = message;
    }

}
