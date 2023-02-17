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

    private Long countChoice;

    private Long countComment;

    private LocalDateTime createdDate;





    @Builder
    public MyPageResponse(Long voteId,String imageA, String imageB, String title, Long countChoice,Long countComment , LocalDateTime createdDate){
        this.voteId = voteId;
        this.imageA = imageA;
        this.imageB = imageB;
        this.title = title;
        this.countChoice =countChoice;
        this.countComment = countComment;
        this.createdDate = createdDate;
    }
}
