package kr.co.chooz.kakao.request;

import lombok.Data;

@Data
public class BearerAuthHeader {
    private String Authorization;

    public BearerAuthHeader(String token) {
        this.Authorization = "Bearer " + token;
    }
}
