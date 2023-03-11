package com.example.manymanyUsers.token.domain;

import com.example.manymanyUsers.common.domain.BaseTimeEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TokenEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "TOKEN_ID")
    private Long id;

    @Column
    private String refreshToken;

    @Column
    private Long userId;
}
