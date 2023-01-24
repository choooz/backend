package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    private String title;

    @Schema(description = "A 이미지")
    private String ImageA;

    @Schema(description = "B 이미지")
    private String ImageB;

    @Schema(description = "투표 상세글")
    private String detail;

    @Schema(description = "투표 받고 싶은 성별")
    private Gender FilteredGender;

    @Schema(description = "투표 받고 싶은 나이대")
    private Age FilteredAge;

    @Schema(description = "투표 카테고리")
    private Category category;

    @Schema(description = "투표 카테고리")
    private MBTI FilteredMbti;

    @Schema(description = "A 항목의 제목")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    private String titleB;
}
