package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateVoteRequest {

//    @Builder
//    public UpdateVoteRequest(Long voteId, String title, String imageA, String imageB, String detail, Gender filteredGender, Age filteredAge, Category category, MBTI filteredMbti, String titleA, String titleB) {
//        this.voteId = voteId;
//        this.title = title;
//        ImageA = imageA;
//        ImageB = imageB;
//        this.detail = detail;
//        FilteredGender = filteredGender;
//        FilteredAge = filteredAge;
//        this.category = category;
//        FilteredMbti = filteredMbti;
//        this.titleA = titleA;
//        this.titleB = titleB;
//    }

    @NotNull
    private Long voteId;

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
