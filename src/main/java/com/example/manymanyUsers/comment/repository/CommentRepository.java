package com.example.manymanyUsers.comment.repository;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByVoteId(Long voteId);

    Slice<Comment> findSliceBy(Pageable pageable); // 페이지 네이션


    //댓글 필터로 거르고 댓글에 속하는 대댓글 모두 가져오는 쿼리
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN c.parent p " +
            "where (c.voteId= :voteId and c.parent is null and (:gender is null or c.gender = :gender) and (:age is null or c.age = :age) and (:mbti is null or c.mbti = :mbti)) " +
            "or (c.voteId= :voteId and c.parent is not null and (:gender is null or p.gender = :gender) and (:age is null or p.age = :age) and (:mbti is null or p.mbti = :mbti))" +
            "order by p.id ASC NULLS FIRST, c.id ASC")
    List<Comment> findCommentsByfilterAndGetChildren(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti);

    //댓글,대댓글 모두 필터에 걸러지는 쿼리
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN c.parent p " +
            "where (c.voteId= :voteId and c.parent is null and (:gender is null or c.gender = :gender) and (:age is null or c.age = :age) and (:mbti is null or c.mbti = :mbti)) " +
            "or (c.voteId= :voteId and c.parent is not null and (:gender is null or ((p.gender = :gender) and (c.gender = :gender))) and (:age is null or ((p.age = :age) and (c.age = :age))) and (:mbti is null or ((p.mbti = :mbti) and (c.mbti = :mbti))))" +
            "order by p.id ASC NULLS FIRST, c.id ASC")
    List<Comment> findCommentsAllByfilter(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti);

    @Query("SELECT c FROM Comment c" +
            " WHERE c.voteId = :voteId and c.parent is null and (:gender is null or c.gender = :gender) and (:age is null or c.age = :age) and (:mbti is null or c.mbti = :mbti)" +
            " ORDER BY (c.likeCount + c.hateCount) DESC , c.createdDate DESC")
    List<Comment> findHotComments(@Param("voteId") Long voteId,@Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti,Pageable pageable);

    @Query("SELECT c FROM Comment c" +
            " WHERE c.voteId = :voteId and c.parent is null and (:gender is null or c.gender = :gender) and (:age is null or c.age = :age) and (:mbti is null or c.mbti = :mbti) " +
            "ORDER BY c.createdDate DESC")
    List<Comment> findNewestComments(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti, Pageable pageable);



}
