package kr.co.chooz.vote.dto;

import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.Category;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoteInfo {
    private VotedUserInfo writer;

    private LocalDateTime voteCreatedDate;

    private Category category;

    private String title;

    private String imageA;

    private String imageB;

    private GenderType filteredGender;

    private AgeType filteredAge;

    private MbtiType filteredMbti;

    private String titleA;

    private String titleB;

    private String description;

    @Builder
    public VoteInfo(VotedUserInfo writer, LocalDateTime voteCreatedDate, Category category, String title, String imageA, String imageB, GenderType filteredGender, AgeType filteredAge, MbtiType filteredMbti, String titleA, String titleB, String description) {
        this.writer = writer;
        this.voteCreatedDate = voteCreatedDate;
        this.category = category;
        this.title = title;
        this.imageA = imageA;
        this.imageB = imageB;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
        this.titleA = titleA;
        this.titleB = titleB;
        this.description = description;
    }
}
