package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUserResponse {

    private Long userId;

    private String image;

    private String nickname;

    private Gender gender;

    private Integer age;

    private Age ageGroup;

    private MBTI mbti;


    public GetUserResponse(Long userId, String image, String nickname, Gender gender, Integer age, Age ageGroup, MBTI mbti) {
        this.userId = userId;
        this.image = image;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.ageGroup = ageGroup;
        this.mbti = mbti;
    }
}
