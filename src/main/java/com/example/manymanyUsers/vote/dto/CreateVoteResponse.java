package com.example.manymanyUsers.vote.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateVoteResponse {

    private Long voteId;

    private String message;

    @Builder
    public CreateVoteResponse(Long voteId, String message) {
        this.voteId = voteId;
        this.message = message;
    }
}
