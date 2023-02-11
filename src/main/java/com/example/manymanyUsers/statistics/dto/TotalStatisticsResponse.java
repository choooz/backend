package com.example.manymanyUsers.statistics.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalStatisticsResponse {

    private Long voteId;
    private Long totalVoteCount;
    private String message;

    @Builder
    public TotalStatisticsResponse(Long voteId, Long totalVoteCount, String message) {
        this.voteId = voteId;
        this.totalVoteCount = totalVoteCount;
        this.message = message;
    }

}
