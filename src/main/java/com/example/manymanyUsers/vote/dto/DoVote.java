package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Choice;
import lombok.Getter;

@Getter
public class DoVote {

    private Long userId;

    private Long voteId;

    private Choice choice;
}
