package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostedUser(User postedUser);

    Slice<Vote> findSliceBy(Pageable pageable);

    Slice<Vote> findByCategory(Category category, Pageable pageable);

    Optional<Vote> findById(Long voteId);

    Slice<Vote> findAllByPostedUser(User user,PageRequest pageRequest);

    @Query("SELECT v FROM Vote v JOIN v.voteResultList vr where vr.votedUser = :user")
    Slice<Vote> findParticipatedVoteByUser(User user,PageRequest pageRequest);


    Long countVoteByPostedUser(User user);

//    List<Vote> findAllByBookmarked(User user);

  
    Slice<Vote> findByCategoryAndTitleContains(Category category, String keyword, Pageable pageable);

    Slice<Vote> findSliceByTitleContains(String keyword, Pageable pageable);


    //    @Query("SELECT v,vr FROM VoteResult vr JOIN fetch vr.vote v GROUP BY v ORDER BY COUNT(v) DESC")
    //    @Query(value = "SELECT v FROM VoteResult vr JOIN fetch vr.vote v")
    //    List<Vote> findSliceByVoteResult();

    @Query("SELECT v FROM Vote v " +
            "left join FETCH v.voteResultList vr " +
            "join FETCH v.postedUser pu " +
            "WHERE v.category IS NULL OR v.category = :category " +
            "GROUP BY v.id, vr.id " +
            "order by count(vr.vote.id) DESC")
    Slice<Vote> findWithVoteResult(@Param("category") Category category, PageRequest pageRequest);

    @Query("SELECT distinct v FROM Vote v " +
            "left join FETCH v.voteResultList vr " +
            "join FETCH v.postedUser pu " +
            "WHERE v.category IS NULL OR v.category = :category AND (v.title LIKE :keyword%)" +
            "GROUP BY v.id, vr.id " +
            "order by count(vr.vote.id) DESC")
    List<Vote> findByCategoryAndTitleContains(@Param("category")Category category, @Param("keyword")String keyword);

}
