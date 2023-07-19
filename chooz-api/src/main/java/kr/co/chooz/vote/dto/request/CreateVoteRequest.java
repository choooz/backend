package kr.co.chooz.vote.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import kr.co.chooz.vote.dto.CreateVoteInfo;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    private String title;

    @Schema(description = "A 항목의 제목")
    @NotBlank
    private String titleA;

    @Schema(description = "B 항목의 제목")
    @NotBlank
    private String titleB;

    @Schema(description = "A 이미지")
    private String imageA;

    @Schema(description = "B 이미지")
    private String imageB;

    @Schema(description = "투표 받고 싶은 성별")
    private GenderType filteredGender;

    @Schema(description = "투표 받고 싶은 나이대")
    private AgeType filteredAge;

    @Schema(description = "투표 받고 싶은 MBTI")
    private MbtiType filteredMbti;

//    @Builder
//    public CreateVoteRequest(String title, String titleA, String titleB, String imageA, String imageB, GenderType filteredGender, AgeType filteredAge, MbtiType filteredMbti) {
//        this.title = title;
//        this.titleA = titleA;
//        this.titleB = titleB;
//        this.imageA = imageA;
//        this.imageB = imageB;
//        this.filteredGender = filteredGender;
//        this.filteredAge = filteredAge;
//        this.filteredMbti = filteredMbti;
//    }

    public CreateVoteInfo toDomain(Long id) {
        return new CreateVoteInfo(id, title, titleA, titleB, imageA, imageB, filteredGender, filteredAge, filteredMbti);
    }
}