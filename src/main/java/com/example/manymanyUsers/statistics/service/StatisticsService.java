package com.example.manymanyUsers.statistics.service;

import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.statistics.dto.VoteSelectResultData;
import com.example.manymanyUsers.timer.Timer;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Choice;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StatisticsService {

    private final VoteResultRepository voteResultRepository;
    private final VoteRepository voteRepository;

    @Timer
    public Long getTotalStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Long totalVoteCount = voteResultRepository.countByVote(vote);

        return totalVoteCount;
    }

    @Timer
    public VoteSelectResultData getSelectStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Long totalVoteCount = voteResultRepository.countByVote(vote);

        int totalCountA = voteResultRepository.countByVoteAndChoice(vote, Choice.A);
        int totalCountB = voteResultRepository.countByVoteAndChoice(vote, Choice.B);

        int percentA = (int) (((float)totalCountA / (float)totalVoteCount) * 100);
        int percentB = (int) (((float)totalCountB / (float)totalVoteCount) * 100);

        VoteSelectResultData voteSelectResultData = new VoteSelectResultData(totalCountA, totalCountB, percentA, percentB);

        return voteSelectResultData;
    }

}
