package com.example.manymanyUsers.vote.service;

import com.example.manymanyUsers.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public Long createVote() {

    }
}
