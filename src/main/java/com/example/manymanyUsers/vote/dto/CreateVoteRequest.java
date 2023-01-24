package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank
    private String title;

    @Schema(description = "A 항목의 제목")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    private String titleB;

    @Schema(description = "A 이미지")
    private String imageA;

    @Schema(description = "B 이미지")
    private String imageB;


    @Builder

    public CreateVoteRequest(String title, String titleA, String titleB, String imageA, String imageB) {
        this.title = title;
        this.titleA = titleA;
        this.titleB = titleB;
        this.imageA = imageA;
        this.imageB = imageB;
    }
}
