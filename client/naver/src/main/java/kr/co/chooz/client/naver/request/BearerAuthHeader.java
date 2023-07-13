package kr.co.chooz.client.naver.request;

import lombok.Data;

@Data
public class BearerAuthHeader {
    private String authorization;

    public BearerAuthHeader(String token) {
        this.authorization = "Bearer " + token;
    }
}