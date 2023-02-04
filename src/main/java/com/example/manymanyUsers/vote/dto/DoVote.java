package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Choice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoVote {

    private Long userId;

    private Long voteId;

    private Choice choice;

    @Builder
    public DoVote(Long userId, Long voteId, Choice choice) {
        this.userId = userId;
        this.voteId = voteId;
        this.choice = choice;
    }
}
