package com.example.manymanyUsers.vote.domain;

import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Vote extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long id;

    /**
     * User 와의 연관관계 주인
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User postedUser;

    /**
     * voteResult 와의 연관관계 주인
     */
    @OneToOne
    @JoinColumn(name = "VOTE_RESULT_ID")
    private VoteResult voteResult;

    @Column
    private String title;

    @Column
    private String ImageA;

    @Column
    private String ImageB;

    @Column
    private String detail;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender filteredGender;

    @Column
    @Enumerated(EnumType.STRING)
    private Age filteredAge;
}
