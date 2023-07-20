package kr.co.chooz.vote.port.out;

import kr.co.chooz.vote.dto.UpdateVoteInfo;
import kr.co.chooz.vote.entity.Vote;
import kr.co.chooz.vote.entity.VoteContent;
import kr.co.chooz.vote.entity.VoteFilter;

public interface VoteStoreRepository {

    public Long register(Vote vote, VoteContent voteContent, VoteFilter voteFilter, Long userId);

    void update(UpdateVoteInfo info);

    void delete(Long voteId);
}
