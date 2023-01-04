package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;

public class VoteListData {

    private Long voteId;

    private Long userId;

    private String totalTitle;

    private Category category;

    private Gender filteredGender;

    private Age filteredAge;

    private MBTI filteredMbti;

}
