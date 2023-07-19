package kr.co.chooz.user.domain.entitiy;

import kr.co.chooz.user.domain.vote.CreateVoteInfo;
import lombok.Getter;

@Getter
public class VoteFilter {

    private Long id;
    private GenderType filteredGender;
    private AgeType filteredAge;
    private MbtiType filteredMbti;

    public VoteFilter(CreateVoteInfo info) {
    }
} 
