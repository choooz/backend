package com.example.manymanyUsers.config.oauth2.kakao.dto;

import com.example.manymanyUsers.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {
    private String token;
    private String message;
    private String statusCode;

    @Builder
    public TokenResponse(String token, String message, String statusCode) {
        this.token = token;
        this.message = message;
        this.statusCode = statusCode;
    }

}
