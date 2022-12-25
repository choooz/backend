package com.example.manymanyUsers.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 랜덤 생성 외부 api 사용시 데이터 받아올 포맷 dto
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserNickNameRequest {

    private String words;

    private String seed;
}
