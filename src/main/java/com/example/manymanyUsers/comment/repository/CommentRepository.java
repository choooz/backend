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
            "WHERE (c.voteId= :voteId AND c.parent IS NULL AND (:gender IS NULL OR c.gender = :gender) AND (:age IS NULL OR c.age = :age) AND (:mbti IS NULL OR c.mbti = :mbti)) " +
            "OR (c.voteId= :voteId AND c.parent is not null AND (:gender IS NULL OR p.gender = :gender) AND (:age IS NULL OR p.age = :age) AND (:mbti IS NULL OR p.mbti = :mbti))" +
            "ORDER BY p.id ASC NULLS FIRST, c.id DESC")
    List<Comment> findCommentsByfilterANDGetChildren(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti);

    //댓글,대댓글 모두 필터에 걸러지는 쿼리
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN c.parent p " +
            "WHERE (c.voteId= :voteId AND c.parent IS NULL AND (:gender IS NULL OR c.gender = :gender) AND (:age IS NULL OR c.age = :age) AND (:mbti IS NULL OR c.mbti = :mbti)) " +
            "OR (c.voteId= :voteId AND c.parent is not null AND (:gender IS NULL OR ((p.gender = :gender) AND (c.gender = :gender))) AND (:age IS NULL OR ((p.age = :age) AND (c.age = :age))) AND (:mbti IS NULL OR ((p.mbti = :mbti) AND (c.mbti = :mbti))))" +
            "ORDER BY p.id ASC NULLS FIRST, c.id DESC")
    List<Comment> findCommentsAllByfilter(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti);

    //인기순 댓글 조회
    @Query("SELECT c FROM Comment c" +
            " WHERE c.voteId = :voteId AND c.parent IS NULL AND (:gender IS NULL OR c.gender = :gender) AND (:age IS NULL OR c.age = :age) AND (:mbti IS NULL OR c.mbti = :mbti)" +
            " ORDER BY (c.likeCount + c.hateCount) DESC , c.createdDate DESC")
    List<Comment> findHotComments(@Param("voteId") Long voteId,@Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti,Pageable pageable);

    //최신순 댓글 조회
    @Query("SELECT c FROM Comment c" +
            " WHERE c.voteId = :voteId AND c.parent IS NULL AND (:gender IS NULL OR c.gender = :gender) AND (:age IS NULL OR c.age = :age) AND (:mbti IS NULL OR c.mbti = :mbti) " +
            "ORDER BY c.createdDate DESC")
    List<Comment> findNewestComments(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti, Pageable pageable);

    //댓글에 속하는 대댓글 조회
    @Query("SELECT c FROM Comment c " +
            "WHERE c.voteId= :voteId AND c.parent= :parentComment AND (:gender IS NULL OR  (c.gender = :gender)) AND (:age IS NULL OR  (c.age = :age)) AND (:mbti IS NULL OR  (c.mbti = :mbti))" +
            "ORDER BY c.id ASC")
    List<Comment> findChildComments(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti, @Param("parentComment") Comment parentComment);

}
