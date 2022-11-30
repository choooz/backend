package com.example.manymanyUsers.config.oauth2.kakao.dto;

import com.example.manymanyUsers.user.domain.Providers;
import com.example.manymanyUsers.user.domain.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 사용자 정보 조회 DTO
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserInfo implements Serializable {

    /**
     * 유저 종류 - KAKAO, NAVER, GOOGLE
     */
    private Providers provider;


    /**
     * Oauth 에서 제공하는 아이디
     */
    private String providerId;

    private String username;

    private String email;

    private String imageUrl;

    private String password;

    private Role role;

    private String message;

    @Builder
    public GetUserInfo(Providers provider, String providerId, String username, String email, String imageUrl, String password, Role role, String message) {
        this.provider = provider;
        this.providerId = providerId;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.role = role;
        this.message = message;
    }
}
