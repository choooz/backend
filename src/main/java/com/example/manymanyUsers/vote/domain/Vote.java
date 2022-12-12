package com.example.manymanyUsers.vote.domain;

import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.domain.User;
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
    private Long id;

    @ManyToOne
    private User postedUser;
}
