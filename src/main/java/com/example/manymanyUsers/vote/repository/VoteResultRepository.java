package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import com.example.manymanyUsers.vote.enums.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

//    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote")
    Long countByVote(@Param("vote") Vote vote);

//    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote and v.choice = com.example.manymanyUsers.vote.enums.Choice.A")
    int countByVoteAndChoice(@Param("vote") Vote vote, @Param("choice") Choice choice);

}
