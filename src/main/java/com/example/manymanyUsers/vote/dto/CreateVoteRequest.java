package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.CategoryList;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateVoteRequest {


    private Long userId;

    @NotBlank
    private String title;

    private String ImageA;

    private String ImageB;

    @NotBlank
    private String detail;

    private Gender FilteredGender;

    private Age FilteredAge;

    private CategoryList category;

    private MBTI FilteredMbti;

    private String titleA;

    private String titleB;

}
