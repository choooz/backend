package com.example.manymanyUsers.statistics.service;

import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.vote.domain.Vote;
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
    public String getTotalStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        String totalVote = String.valueOf(voteResultRepository.findTotalVoteById(vote));
        return totalVote;

    }
}
