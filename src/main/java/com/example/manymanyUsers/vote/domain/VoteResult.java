package com.example.manymanyUsers.vote.domain;

import com.example.manymanyUsers.comment.domain.Comment;
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

    /**
     * Vote 와의 연관관계 주인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOTE_ID")
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

    public void doVote(Vote vote, User user, Choice choice) {
        this.vote = vote;
        vote.addVoteResult(this);
        this.votedUser = user;
        this.choice = choice;
    }

}
