package kr.co.chooz.user.dto;

public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private boolean isNewUser;

    public TokenResponse(LoginToken loginToken) {
        accessToken = loginToken.getAccessToken();
        refreshToken = loginToken.getRefreshToken();
        isNewUser = loginToken.isNewUser();
    }
}
