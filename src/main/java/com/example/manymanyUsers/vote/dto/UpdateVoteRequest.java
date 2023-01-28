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

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    private String title;

    @Schema(description = "투표 상세글")
    private String detail;

    @Schema(description = "투표 카테고리")
    private Category category;

    @Schema(description = "A 항목의 제목")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    private String titleB;
}
