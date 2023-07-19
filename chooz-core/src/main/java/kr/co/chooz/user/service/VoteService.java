package kr.co.chooz.user.service;

import kr.co.chooz.user.domain.entitiy.Vote;
import kr.co.chooz.user.domain.entitiy.VoteContent;
import kr.co.chooz.user.domain.entitiy.VoteFilter;
import kr.co.chooz.user.domain.vote.CreateVoteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteService {

    private final VoteRegister voteRegister;

    public Long createVote(CreateVoteInfo info) {

        Vote vote = new Vote(info);
        List<VoteContent> voteContentList = new ArrayList<>();
        VoteFilter voteFilter = new VoteFilter(info);

    }

    public void isUserVote(Vote vote, Long userId) {



    }

}
