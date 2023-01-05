package com.example.manymanyUsers.comment.repository;


import com.example.manymanyUsers.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByVoteId(Long voteId);

//    @Query("SELECT c from Comment c where c.voteId=:voteId and c.id>0 order by c.id ASC ")
//    public List<Comment> getCommentlist(@Param("voteId") Long voteId);

}
