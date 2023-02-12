package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
public class GetVoteUserResponse {
    private String userImage;

    private Gender userGender;

    private Age userAge;

    private MBTI userMbti;

    private String nickName;

    @Builder
    public GetVoteUserResponse(String userImage, Gender userGender, Age userAge, MBTI userMbti, String nickName) {
        this.userImage = userImage;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userMbti = userMbti;
        this.nickName = nickName;
    }

}
