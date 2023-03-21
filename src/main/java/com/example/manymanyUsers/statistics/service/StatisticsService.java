package com.example.manymanyUsers.statistics.service;

import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.statistics.dto.VoteSelectResultData;
import com.example.manymanyUsers.common.timer.Timer;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Choice;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import com.example.manymanyUsers.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StatisticsService {

    private final VoteResultRepository voteResultRepository;
    private final VoteRepository voteRepository;

    @Timer
    @Cacheable(value = "totalStatistics", key = "#voteId")
    public Long getTotalStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Long totalVoteCount = voteResultRepository.countByVote(vote);

        return totalVoteCount;
    }

    @Timer
    public VoteSelectResultData getSelectedStatistics(Long voteId, Gender gender, Age age, MBTI mbti) {

        Integer classifyAge = null;

        if(age != null)
            classifyAge = Integer.valueOf(age.getValue().substring(0, 2));

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        int totalCountA = voteResultRepository.countByVoteAndChoiceAndGenderAndAgeAndMBTI(vote, Choice.A, gender, classifyAge, mbti);
        int totalCountB = voteResultRepository.countByVoteAndChoiceAndGenderAndAgeAndMBTI(vote, Choice.B, gender, classifyAge, mbti);

        float totalVoteCount = totalCountA + totalCountB;

        int percentA = (int) (((float)totalCountA / totalVoteCount) * 100);
        int percentB = (int) (((float)totalCountB / totalVoteCount) * 100);

        VoteSelectResultData voteSelectResultData = new VoteSelectResultData(totalCountA, totalCountB, percentA, percentB);

        return voteSelectResultData;
    }

}
