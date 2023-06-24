package kr.co.chooz.token.dto;

import lombok.Getter;

@Getter
public class TokenGenerateResponse {

    private String acessToken;
    private String refreshToken;

    public TokenGenerateResponse(String acessToken, String refreshToken) {
        this.acessToken = acessToken;
        this.refreshToken = refreshToken;
    }
}
