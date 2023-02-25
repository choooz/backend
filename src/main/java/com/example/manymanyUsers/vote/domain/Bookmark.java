package com.example.manymanyUsers.vote.domain;


import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOTE_ID")
    private Vote vote;

    public void mappingVote(Vote vote) {
        this.vote = vote;
        vote.mappingBookmark(this);
    }

    public void mappingUser(User user) {
        this.user = user;
        user.mappingBookmark(this);
    }


}
