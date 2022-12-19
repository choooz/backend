package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByProviderId(String providerId);
}
