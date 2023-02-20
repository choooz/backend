package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Choice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

//    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote")
    Long countByVote(@Param("vote") Vote vote);

//    @Query("SELECT COUNT(*) FROM VoteResult v WHERE v.vote = :vote and v.choice = com.example.manymanyUsers.vote.enums.Choice.A")
    int countByVoteAndChoice(@Param("vote") Vote vote, @Param("choice") Choice choice);

    boolean existsByVoteAndVotedUser(@Param("vote")Vote vote, @Param("votedUser")User user);

    VoteResult findByVote(Vote vote);


    @Query("SELECT vr FROM VoteResult vr " +
            "left join FETCH vr.vote v " +
            "WHERE v.category IS NULL OR v.category = :category " +
            "GROUP BY v.id, vr.id " +
            "order by count(vr.vote.id) DESC")
    Slice<VoteResult> findWithVoteFROMResult(@Param("category") Category category, PageRequest pageRequest);
}
