package com.example.manymanyUsers.statistics.service;

import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.statistics.dto.VoteSelectResultData;
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
    public int getTotalStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        int totalVote = voteResultRepository.countByVote(vote);

        return totalVote;
    }

    public VoteSelectResultData getSelectStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        int totalVote = voteResultRepository.countByVote(vote);

//        int totalCountA = voteResultRepository.countChoiceAByVote(vote);
//        int totalCountB = voteResultRepository.countChoiceBByVote(vote);

        int totalCountA = voteResultRepository.countByVoteAndChoice(vote, Choice.A);
        int totalCountB = voteResultRepository.countByVoteAndChoice(vote, Choice.B);

        int percentA = (int) (((float)totalCountA / (float)totalVote) * 100);
        int percentB = (int) (((float)totalCountB / (float)totalVote) * 100);

        VoteSelectResultData voteSelectResultData = new VoteSelectResultData(totalCountA, totalCountB, percentA, percentB);

        return voteSelectResultData;
    }

}
