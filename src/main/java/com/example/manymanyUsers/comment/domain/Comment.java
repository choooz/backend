package com.example.manymanyUsers.comment.domain;


import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.enums.Emotion;
import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User commentUser;

    @Column
    private Long voteId;
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column
    private Age age;

    @Enumerated(EnumType.STRING)
    @Column
    private MBTI mbti;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;
    @Column
    private Long likeCount;
    @Column
    private Long hateCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private List<CommentEmotion> commentEmotionList = new ArrayList<>();

    public void mappingCommentEmotion(CommentEmotion commentEmotion) {
        this.commentEmotionList.add(commentEmotion);
    }

    public void updateLikeHateCount() {
        int likecnt = 0;
        int hatecnt = 0;
        for (CommentEmotion commentEmotion : commentEmotionList) {
            if (commentEmotion.getEmotion().equals(Emotion.LIKE)) {
                likecnt += 1;
            } else {
                hatecnt += 1;
            }
        }
        this.likeCount = (long) likecnt;
        this.hateCount = (long) hatecnt;
    }

    public void removeEmotion(CommentEmotion commentEmotion) {
        this.commentEmotionList.remove(commentEmotion);
    }

    public void update(CommentUpdateRequest commentUpdateRequest) {
        this.content = commentUpdateRequest.getContent();
    }

    public void updateParent(Comment parent) {
        this.parent = parent;
    }


}
