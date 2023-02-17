package com.example.manymanyUsers.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageCountResponse {

    Long countCreatedVote;

    Long countParticipatedVote;

//    Long countBookmarkedVote;

    public MyPageCountResponse(Long countCreatedVote, Long countParticipatedVote){
        this.countCreatedVote = countCreatedVote;
        this.countParticipatedVote = countParticipatedVote;
    }
}
