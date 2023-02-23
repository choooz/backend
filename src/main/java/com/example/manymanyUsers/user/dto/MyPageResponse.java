package com.example.manymanyUsers.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyPageResponse {
    private Long voteId;
    private String imageA;

    private String imageB;

    private String title;

    private Long countVoted;

    private Long countComment;

    private LocalDateTime modifiedDate;





    @Builder
    public MyPageResponse(Long voteId, String imageA, String imageB, String title, Long countVoted, Long countComment , LocalDateTime modifiedDate){
        this.voteId = voteId;
        this.imageA = imageA;
        this.imageB = imageB;
        this.title = title;
        this.countVoted = countVoted;
        this.countComment = countComment;
        this.modifiedDate = modifiedDate;
    }
}
