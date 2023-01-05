package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.dto.UserData;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.Getter;

@Getter
public class VoteListData {

    private Long voteId;

    private UserData userData;

    private String totalTitle;

    private Category category;

    private Gender filteredGender;

    private Age filteredAge;

    private MBTI filteredMbti;

    public VoteListData(final Vote vote) {
        this.voteId = vote.getId();
        this.totalTitle = totalTitle;
        this.category = category;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;

        UserData userData = new UserData(vote.getPostedUser());
        this.userData = userData;
    }
}
