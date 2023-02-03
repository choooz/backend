package com.example.manymanyUsers.vote.domain;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.enums.Choice;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteResult {

    @Id
    @GeneratedValue
    @Column(name = "VOTE_RESULT_ID")
    private Long id;

    @OneToOne(mappedBy = "voteResult", fetch = FetchType.LAZY)
    private Vote vote;

    /**
     * User 와의 연관관계 주인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User votedUser;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Choice choice;

    @Builder
    public VoteResult(Vote vote, User votedUser, Choice choice) {
        this.vote = vote;
        this.votedUser = votedUser;
        this.choice = choice;
    }
}
