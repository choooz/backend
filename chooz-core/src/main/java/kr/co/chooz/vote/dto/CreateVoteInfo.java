package kr.co.chooz.vote.dto;

import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import lombok.Getter;

@Getter
public class CreateVoteInfo {

    private Long userId;
    private String title;
    private String titleA;
    private String titleB;
    private String imageA;
    private String imageB;
    private GenderType filteredGender;
    private AgeType filteredAge;
    private MbtiType filteredMbti;

    public CreateVoteInfo(Long userId, String title, String titleA, String titleB, String imageA, String imageB, GenderType filteredGender, AgeType filteredAge, MbtiType filteredMbti) {
        this.userId = userId;
        this.title = title;
        this.titleA = titleA;
        this.titleB = titleB;
        this.imageA = imageA;
        this.imageB = imageB;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
    }
}
