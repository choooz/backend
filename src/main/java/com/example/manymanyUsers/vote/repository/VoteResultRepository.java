package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.vote.domain.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {



}
