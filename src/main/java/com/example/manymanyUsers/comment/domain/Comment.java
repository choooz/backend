package com.example.manymanyUsers.comment.domain;


import com.example.manymanyUsers.comment.dto.CommentRequest;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @Column
    private String age;

    @Enumerated(EnumType.STRING)
    @Column
    private MBTI mbti;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private Long likeCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentLike> postLikeList = new ArrayList<>();


    public void addCommentLike(CommentLike commentLike) {
        this.postLikeList.add(commentLike);
    }

    public void updateLikeCount() {
        this.likeCount = (long) this.postLikeList.size();
    }

    public void discountLike(CommentLike commentLike) {
        this.postLikeList.remove(commentLike);

    }



    public void update(CommentRequest commentRequest) {
        this.content = commentRequest.getContent();
    }

    public String ClassifyAge(Integer age){
        String ageGroup = "";
        switch (age/10){
            case 0:
                ageGroup = "10대 미만";
                break;
            case 1:
                ageGroup = "10대";
                break;
            case 2:
                ageGroup = "20대";
                break;
            case 3:
                ageGroup = "30대";
                break;
            case 4:
                ageGroup = "40대";
                break;
            case 5:
                ageGroup = "50대";
                break;
            case 6:
                ageGroup = "60대";
                break;
            case 7:
                ageGroup = "70대";
                break;
            case 8:
                ageGroup = "80대";
                break;
            case 9:
                ageGroup = "90대";
                break;
            default:
                ageGroup = "90대 이상";
                break;
        }
        return ageGroup;
    }

}
