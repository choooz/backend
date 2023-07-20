package kr.co.chooz.vote.service;

import kr.co.chooz.vote.dto.UpdateVoteInfo;
import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.entity.VoteContent;
import kr.co.chooz.vote.entity.VoteFilter;
import kr.co.chooz.vote.entity.Vote;
import kr.co.chooz.vote.dto.CreateVoteInfo;
import kr.co.chooz.vote.port.out.VoteReadRepository;
import kr.co.chooz.vote.port.out.VoteStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService {

    private final VoteStoreRepository voteStoreRepository;

    private final VoteReadRepository voteReadRepository;

    public Long createVote(CreateVoteInfo info) {

        Vote vote = new Vote(info);
        VoteContent voteContent = new VoteContent(info);
        VoteFilter voteFilter = new VoteFilter(info);

        return voteStoreRepository.register(vote, voteContent, voteFilter, info.getUserId());
    }

    public VoteInfo getVote(Long voteId) {

        return voteReadRepository.getVote(voteId);

    }

    public void updateVote(UpdateVoteInfo info) {

        isUserVote(info.getUserId(), info.getVoteId());
        voteStoreRepository.update(info);

    }

    public void deleteVote(Long voteId, Long userId) {

        isUserVote(voteId, userId);
        voteStoreRepository.delete(voteId);

    }

    public void isUserVote(Long userId, Long voteId) {

        if(!voteReadRepository.isUserVote(userId, voteId)) {
            throw new RuntimeException();
        }
    }



}
