package com.example.manymanyUsers.config.oauth2.kakao.dto;

import com.example.manymanyUsers.user.domain.CategoryEntity;
import com.example.manymanyUsers.user.enums.Providers;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 정보 조회 DTO
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserInfoResponse implements Serializable {

    private Long userId;

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

    private Integer age;

    private Gender gender;

    private MBTI mbti;

    private List<CategoryEntity> interestedCategory = new ArrayList<>();

    private String message;

    @Builder
    public GetUserInfoResponse(Long userId, Providers provider, String providerId, String username, String email, String imageUrl, Integer age, Gender gender, MBTI mbti, List<CategoryEntity> interestedCategory, String message) {
        this.userId = userId;
        this.provider = provider;
        this.providerId = providerId;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
        this.interestedCategory = interestedCategory;
        this.message = message;
    }
}
