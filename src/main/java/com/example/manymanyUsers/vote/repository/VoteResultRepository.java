package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

//    해당 투표의 투표결과 개수 가져오는 쿼리
//    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote")
    int countByVote(@Param("vote") Vote vote);

//    해당 투표의 투표결과 A 개수 가져오는 쿼리
    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote and v.choice = com.example.manymanyUsers.vote.enums.Choice.A")
    int countChoiceAByVote(@Param("vote") Vote vote);

//    해당 투표의 투표결과 B 개수 가져오는 쿼리
    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote and v.choice = com.example.manymanyUsers.vote.enums.Choice.B")
    int countChoiceBByVote(@Param("vote") Vote vote);
}
