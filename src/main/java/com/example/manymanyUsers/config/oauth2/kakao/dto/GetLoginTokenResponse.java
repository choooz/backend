package com.example.manymanyUsers.config.oauth2.kakao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetLoginTokenResponse {

    private String accessToken;

    private boolean isNewUser;

    @Builder
    public GetLoginTokenResponse(String accessToken, boolean isNewUser) {
        this.accessToken = accessToken;
        this.isNewUser = isNewUser;
    }
}
