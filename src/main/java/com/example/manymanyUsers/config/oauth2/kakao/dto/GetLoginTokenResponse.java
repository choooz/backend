package com.example.manymanyUsers.config.oauth2.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GetLoginTokenResponse {

    private String accessToken;
    private String refreshToken;
    private boolean isNewUser;

}
