package kr.co.chooz.vote.port.in;

import kr.co.chooz.vote.dto.CreateVoteInfo;
import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteUseCase {

    private final VoteService voteService;

    public Long createVote(CreateVoteInfo info) {

        return voteService.createVote(info);

    }

    public VoteInfo getVote(Long voteId) {

        return voteService.getVote(voteId);

    }
}
