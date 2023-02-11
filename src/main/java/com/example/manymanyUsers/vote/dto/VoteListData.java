package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.user.dto.UserData;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoteListData {

    private final Long voteId;

    private final UserData writer;

    private final String totalTitle;

    private final Category category;

    private final Gender filteredGender;

    private final Age filteredAge;

    private final MBTI filteredMbti;

    private final String imageA;

    private final String imageB;

    private final LocalDateTime modifiedDate;

    public VoteListData(final Vote vote) {
        this.voteId = vote.getId();
        this.totalTitle = vote.getTotalTitle();
        this.category = vote.getCategory();
        this.filteredGender = vote.getFilteredGender();
        this.filteredAge = vote.getFilteredAge();
        this.filteredMbti = vote.getFilteredMbti();
        this.imageA = vote.getImageA();
        this.imageB = vote.getImageB();
        this.modifiedDate = vote.getModifiedDate();

        UserData userData = new UserData(vote.getPostedUser());
        this.writer = userData;
    }
}
