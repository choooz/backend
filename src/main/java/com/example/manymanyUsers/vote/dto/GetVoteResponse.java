package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetVoteResponse {

    private String userImage;

    private Gender userGender;

    private Age userAge;

    private MBTI userMbti;

    private String nickName;

    private LocalDateTime voteCreatedDate;

    private Category category;

    private String title;

    private String imageA;

    private String imageB;

    private Gender filteredGender;

    private Age filteredAge;

    private MBTI filteredMbti;

    private String titleA;

    private String titleB;

    private String description;

    @Builder
    public GetVoteResponse(String userImage, Gender userGender, Age userAge, MBTI userMbti, String nickName, LocalDateTime voteCreatedDate, Category category, String title, String imageA
    ,String imageB, Gender filteredGender, Age filteredAge, MBTI filteredMbti, String titleA, String titleB, String description){
        this.userImage = userImage;
        this.userGender = userGender;
        this.userAge = userAge;
        this.userMbti = userMbti;
        this.nickName = nickName;
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
