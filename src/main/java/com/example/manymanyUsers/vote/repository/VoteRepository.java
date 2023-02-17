package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostedUser(User postedUser);

    Slice<Vote> findSliceBy(Pageable pageable);

    Slice<Vote> findByCategory(Category category, Pageable pageable);

    Optional<Vote> findById(Long voteId);

    List<Vote> findAllByPostedUser(User user);

    @Query("SELECT v FROM Vote v JOIN v.voteResultList vr where vr.votedUser = :user")
    List<Vote> findParticipatedVoteByUser(User user);

    Long countVoteByPostedUser(User user);

//    List<Vote> findAllByBookmarked(User user);



}
