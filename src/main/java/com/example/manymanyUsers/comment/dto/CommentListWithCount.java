package com.example.manymanyUsers.comment.dto;


import com.example.manymanyUsers.comment.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListWithCount {
    List<Comment> comments;
    int commentCount;

    public CommentListWithCount(List<Comment> comments, int commentCount) {
        this.comments = comments;
        this.commentCount = commentCount;
    }
}
