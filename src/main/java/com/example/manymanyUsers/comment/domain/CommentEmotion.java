package com.example.manymanyUsers.comment.domain;

import com.example.manymanyUsers.comment.enums.Emotion;
import com.example.manymanyUsers.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentEmotion {
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

    @Column
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @Builder
    public CommentEmotion(User user, Comment comment, Emotion emotion){
        this.user =user;
        this.comment = comment;
        this.emotion = emotion;
    }

    public void mappingComment(Comment comment) {
        this.comment = comment;
        comment.mappingCommentEmotion(this);
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingCommentLike(this);
    }
    public void setEmotionLike(){
        this.emotion = Emotion.LIKE;
    }
    public void setEmotionHate(){
        this.emotion = Emotion.HATE;
    }
}
