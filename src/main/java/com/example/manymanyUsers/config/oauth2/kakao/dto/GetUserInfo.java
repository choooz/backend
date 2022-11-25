package com.example.manymanyUsers.config.oauth2.kakao.dto;

import com.example.manymanyUsers.user.domain.Providers;
import com.example.manymanyUsers.user.domain.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 사용자 정보 조회 DTO
 */
@ToString
@Builder
@Getter
@RequiredArgsConstructor
public class GetUserInfo implements Serializable {

    /**
     * 유저 종류 - KAKAO, NAVER, GOOGLE
     */
    private final Providers provider;


    /**
     * Oauth 에서 제공하는 아이디
     */
    private final String providerId;

    private final String username;

    private final String email;

    private final String imageUrl;

    private final String password;

    private final Role role;


}
