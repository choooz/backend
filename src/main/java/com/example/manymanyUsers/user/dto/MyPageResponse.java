package com.example.manymanyUsers.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MyPageResponse {
    private String imageA;

    private String imageB;

    private String title;

    private Long countChoice;

    private Long countComment;

    private LocalDateTime createdDate;





    @Builder
    public MyPageResponse(String imageA, String imageB, String title, Long countChoice,Long countComment , LocalDateTime createdDate){
        this.imageA = imageA;
        this.imageB = imageB;
        this.title = title;
        this.countChoice =countChoice;
        this.countComment = countComment;
        this.createdDate = createdDate;
    }
}
