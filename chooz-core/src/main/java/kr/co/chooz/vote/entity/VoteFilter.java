package kr.co.chooz.vote.entity;

import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import kr.co.chooz.vote.dto.CreateVoteInfo;
import lombok.Getter;

@Getter
public class VoteFilter {

    private Long id;
    private GenderType filteredGender;
    private AgeType filteredAge;
    private MbtiType filteredMbti;

    public VoteFilter(CreateVoteInfo info) {

        this.filteredGender = info.getFilteredGender();
        this.filteredAge = info.getFilteredAge();
        this.filteredMbti = info.getFilteredMbti();

    }
} 
