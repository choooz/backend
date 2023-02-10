package com.example.manymanyUsers.statistics.service;

import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.statistics.dto.TotalStatisticsResponse;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StatisticsService {

    private final VoteResultRepository voteResultRepository;
    private final VoteRepository voteRepository;
    public TotalStatisticsResponse getTotalStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        int totalVote = voteResultRepository.countByVote(vote);
        int totalACount = voteResultRepository.countChoiceAByVote(vote);
        int totalBCount = voteResultRepository.countChoiceBByVote(vote);

        int percentA = (int) (((float)totalACount / (float)totalVote) * 100);
        int percentB = (int) (((float)totalBCount / (float)totalVote) * 100);

        System.out.println("ACount = " + totalACount);
        System.out.println("BCount = " + totalBCount);
        System.out.println("percentA = " + percentA);
        System.out.println("percentB = " + percentB);

        TotalStatisticsResponse totalStatisticsResponse = TotalStatisticsResponse.builder()
                .voteId(voteId)
                .totalVote(totalVote)
                .aCount(totalACount)
                .aPercentage(percentA)
                .bCount(totalBCount)
                .bPercentage(percentB)
                .message("해당 voteId 투표 참여 인원 통계 조회에 성공했습니")
                .build();
        return totalStatisticsResponse;

    }
}
