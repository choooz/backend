package com.example.manymanyUsers.config.oauth2.kakao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 사용자 정보 조회 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetUserInfo implements Serializable {

    /**
     * 사용자 아이디
     */
    private String email;
}
