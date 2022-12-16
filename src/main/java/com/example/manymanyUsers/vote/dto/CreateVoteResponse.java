package com.example.manymanyUsers.vote.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateVoteResponse {
    private String message;

    @Builder
    public CreateVoteResponse(String message) {
        this.message = message;
    }
}
