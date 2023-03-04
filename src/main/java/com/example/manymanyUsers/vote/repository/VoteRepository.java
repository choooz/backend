package com.example.manymanyUsers.vote.repository;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.dto.FindVoteListData;
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


    @Query("SELECT new com.example.manymanyUsers.vote.dto.FindVoteListData(v,(SELECT count(vr.vote) FROM VoteResult vr WHERE vr.vote = v))" +
            "FROM Vote v WHERE (:category is null or v.category = :category)")
    Slice<FindVoteListData> findSliceBy(@Param("category") Category category ,Pageable pageable);

    Slice<Vote> findAllByPostedUser(User user,PageRequest pageRequest);

    @Query("SELECT v FROM Vote v JOIN v.voteResultList vr WHERE vr.votedUser = :user")
    Slice<Vote> findParticipatedVoteByUser(User user,PageRequest pageRequest);


    @Query("SELECT v FROM Vote v " +
            "join v.postedUser " +
            "where v.id = :voteId")
    Optional<Vote> findById(@Param("voteId") Long voteId);

    Long countVoteByPostedUser(User user);

    @Query("SELECT v FROM Vote v JOIN v.bookmarkList b WHERE b.user = :user")
    Slice<Vote> findBookmarkedVoteByUser(User user, PageRequest pageRequest);


  
    Slice<Vote> findByCategoryAndTitleContains(Category category, String keyword, Pageable pageable);

    Slice<Vote> findSliceByTitleContains(String keyword, Pageable pageable);

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
