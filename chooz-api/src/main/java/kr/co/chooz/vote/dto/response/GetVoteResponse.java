package kr.co.chooz.vote.dto.response;

import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.Category;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.dto.VotedUserInfo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetVoteResponse {

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

    public GetVoteResponse(VoteInfo voteInfo) {
        this.writer = voteInfo.getWriter();
        this.voteCreatedDate = voteInfo.getVoteCreatedDate();
        this.category = voteInfo.getCategory();
        this.title = voteInfo.getTitle();
        this.imageA = voteInfo.getImageA();
        this.imageB = voteInfo.getImageB();
        this.filteredGender = voteInfo.getFilteredGender();
        this.filteredAge = voteInfo.getFilteredAge();
        this.filteredMbti = voteInfo.getFilteredMbti();
        this.titleA = voteInfo.getTitleA();
        this.titleB = voteInfo.getTitleB();
        this.description = voteInfo.getDescription();
    }
}
