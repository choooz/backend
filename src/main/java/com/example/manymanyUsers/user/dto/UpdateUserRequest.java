package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    String nickname;

    String image;

    MBTI mbti;

    List<Category> categoryList;


    public UpdateUserRequest(String nickname, String image, MBTI mbti, List<Category> categoryList) {
        this.nickname = nickname;
        this.image = image;
        this.mbti = mbti;
        this.categoryList = categoryList;
    }
}
