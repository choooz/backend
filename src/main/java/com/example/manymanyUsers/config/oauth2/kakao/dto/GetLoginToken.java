package com.example.manymanyUsers.config.oauth2.kakao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLoginToken {

    private String token;

    private boolean isNewUser;

    @Builder
    public GetLoginToken(String token, boolean isNewUser) {
        this.token = token;
        this.isNewUser = isNewUser;
    }
}
