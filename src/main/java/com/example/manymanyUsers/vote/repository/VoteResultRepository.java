package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.domain.VoteResult;
import com.example.manymanyUsers.vote.enums.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteResultRepository extends JpaRepository<VoteResult, Long> {

    Long countByVote(@Param("vote") Vote vote);

    @Query("SELECT COUNT(*) " +
            "FROM VoteResult v JOIN v.votedUser u " +
            "WHERE (v.vote = :vote) and (v.choice = :choice) " +
            "AND (:gender is null or u.gender = :gender) AND (:age is null or u.age = :age) AND (:mbti is null or u.mbti = :mbti)")
    int countByVoteAndChoiceAndGenderAndAgeAndMBTI(@Param("vote") Vote vote, @Param("choice") Choice choice, @Param("gender") Gender gender, @Param("age") Integer age, @Param("mbti") MBTI mbti);

    boolean existsByVoteAndVotedUser(@Param("vote")Vote vote, @Param("votedUser")User user);

    VoteResult findByVote(Vote vote);

  
    Long countByVotedUser(User user);


    @Query("SELECT vr FROM VoteResult vr " +
            "left join FETCH vr.vote v " +
            "WHERE v.category IS NULL OR v.category = :category " +
            "GROUP BY v.id, vr.id " +
            "order by count(vr.vote.id) DESC")
    Slice<VoteResult> findWithVoteFROMResult(@Param("category") Category category, PageRequest pageRequest);


    @Query(value = "SELECT * FROM vote_result where user_Id = :userId",nativeQuery = true)
    List<VoteResult> findByVotedUserId(@Param("userId") Long userId);


    @Query("SELECT vr FROM VoteResult vr WHERE vr.vote.id = :voteId and vr.votedUser.id = :userId")
    Optional<VoteResult> getVoteResultByVoteIdAndUserId(@Param("voteId") Long voteId, @Param("userId") Long userId);


}
