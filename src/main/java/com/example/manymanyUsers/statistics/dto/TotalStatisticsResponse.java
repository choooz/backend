package com.example.manymanyUsers.statistics.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalStatisticsResponse {

    private Long voteId;
    private String totalVote;
    private String message;

    @Builder
    public TotalStatisticsResponse(Long voteId, String totalVote, String message) {
        this.voteId = voteId;
        this.totalVote = totalVote;
        this.message = message;
    }

}
