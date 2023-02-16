package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostedUser(User postedUser);

    Slice<Vote> findSliceBy(Pageable pageable);

    Slice<Vote> findByCategory(Category category, Pageable pageable);

    Optional<Vote> findById(Long voteId);

    List<Vote> findAllByPostedUser(User user);

    List<Vote> findAllByVoteResultList(User user);

    List<Vote> findAllByBookmarked(User user);



}
