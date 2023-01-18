package com.example.manymanyUsers.comment.domain;


import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private List<CommentLike> commentLikeList = new ArrayList<>();


    public void mappingCommentLike(CommentLike commentLike){
        this.commentLikeList.add(commentLike);
    }

    public void updateLikeCount() {
        this.likeCount = (long) this.commentLikeList.size();
    }

    public void discountLike(CommentLike commentLike) {
        this.commentLikeList.remove(commentLike);
    }



    public void update(CommentUpdateRequest commentUpdateRequest) {
        this.content = commentUpdateRequest.getContent();
    }

    public void updateParent(Comment parent){
        this.parent = parent;
    }

    public void ClassifyAge(Integer age){
        Age ageGroup;
        switch (age/10){
            case 1:
                ageGroup = Age.teenager;
                break;
            case 2:
                ageGroup = Age.twenties;
                break;
            case 3:
                ageGroup = Age.thirties;
                break;
            case 4:
                ageGroup = Age.fourties;
                break;
            case 5:
                ageGroup = Age.fifties;
                break;
            default:
                ageGroup = Age.NULL;
                break;
        }
        this.age = ageGroup;
    }

}
