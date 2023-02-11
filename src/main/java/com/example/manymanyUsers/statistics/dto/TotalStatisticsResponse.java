package com.example.manymanyUsers.statistics.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalStatisticsResponse {

    private Long voteId;
    private Long totalVote;
    private String message;

    @Builder
    public TotalStatisticsResponse(Long voteId, Long totalVote, String message) {
        this.voteId = voteId;
        this.totalVote = totalVote;
        this.message = message;
    }

}
