package com.example.manymanyUsers.config.oauth2.kakao.dto;

import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 유저 정보 추가 기입 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddInfoRequest {

    private Long userId;

    @NotBlank
    private MBTI mbti;

    private Integer age;

    @NotBlank
    private Gender gender;

}
