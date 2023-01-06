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

import javax.persistence.*;

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

    public void update(CommentRequest commentRequest) {
        this.content = commentRequest.getContent();
    }

    public String ClassifyAge(Integer age){
        String ageGroup = "";
        switch (age/10){
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
        }
        return ageGroup;
    }

}
