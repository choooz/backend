package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateVoteRequest {

    @NotBlank
    private Long voteId;

    private Long userId;

    private String title;

    private String ImageA;

    private String ImageB;

    private String detail;

    private Gender FilteredGender;

    private Age FilteredAge;

    private Category category;

    private MBTI FilteredMbti;

    private String titleA;

    private String titleB;
}
