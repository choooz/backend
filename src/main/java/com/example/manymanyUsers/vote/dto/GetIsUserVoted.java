package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Choice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class GetIsUserVoted {

    private boolean isVoted;

    private Choice userChoice;
}
