package com.example.manymanyUsers.comment.repository;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.vote.domain.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByVoteId(Long voteId);

    Slice<Comment> findSliceBy(Pageable pageable); // 페이지 네이션


//    @Query("SELECT c from Comment c where c.voteId=:voteId and c.id>0 order by c.id ASC ")
//    public List<Comment> getCommentlist(@Param("voteId") Long voteId);

}
