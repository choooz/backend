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

    @Query("select c from Comment c where c.voteId= :voteId and (:gender is null or c.gender = :gender) and (:age is null or c.age = :age) and (:mbti is null or c.mbti = :mbti)")
    List<Comment> filteredComments(@Param("voteId") Long voteId, @Param("gender") Gender gender, @Param("age") Age age, @Param("mbti") MBTI mbti);

    @Query("SELECT c FROM Comment c LEFT JOIN c.parent p ORDER BY p.id ASC NULLS FIRST, c.id ASC ")
    List<Comment> findAllOrderByParentIdAscNullsFirstCommentIdAsc();





//    @Query("SELECT c from Comment c where c.voteId=:voteId and c.id>0 order by c.id ASC ")
//    public List<Comment> getCommentlist(@Param("voteId") Long voteId);

}
