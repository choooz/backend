package kr.co.chooz.vote.port.out;

import kr.co.chooz.vote.dto.VoteInfo;

public interface VoteReadRepository {
    VoteInfo getVote(Long voteId);
}
