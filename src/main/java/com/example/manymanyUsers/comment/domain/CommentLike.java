package com.example.manymanyUsers.comment.domain;

import com.example.manymanyUsers.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="COMMENT_ID")
    private Comment comment;

    @Builder
    public CommentLike(User user, Comment comment){
        this.user =user;
        this.comment = comment;
    }

    public void mappingPost(Comment comment) {
        this.comment = comment;
        comment.mappingCommentLike(this);
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingCommentLike(this);
    }
}
