package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Choice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DoVoteRequest {

    private Long voteId;

    private Choice choice;


    public DoVote converter(Long userId) {
        return DoVote.builder()
                .userId(userId)
                .choice(choice)
                .voteId(voteId)
                .build();
    }

}
