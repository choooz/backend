package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Choice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoVoteRequest {

    private Choice choice;

    public DoVote converter(Long userId, Long voteId) {
        return DoVote.builder()
                .userId(userId)
                .voteId(voteId)
                .choice(choice)
                .build();
    }

    public DoVoteRequest(Choice choice) {
        this.choice = choice;
    }
}
