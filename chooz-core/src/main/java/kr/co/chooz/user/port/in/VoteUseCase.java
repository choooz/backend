package kr.co.chooz.user.port.in;

import kr.co.chooz.user.domain.vote.CreateVoteInfo;
import kr.co.chooz.user.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteUseCase {

    private final VoteService voteService;

    public Long createVote(CreateVoteInfo info) {

        return voteService.createVote(info);

    }
}
