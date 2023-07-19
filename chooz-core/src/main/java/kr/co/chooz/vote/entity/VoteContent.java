package kr.co.chooz.vote.entity;

import kr.co.chooz.vote.dto.CreateVoteInfo;
import lombok.Getter;

@Getter

public class VoteContent {

    private Long id;
    private String imageA;
    private String imageB;
    private String titleA;
    private String titleB;


    public VoteContent(CreateVoteInfo info) {

        this.imageA = info.getImageA();
        this.imageB = info.getImageB();
        this.titleA = info.getTitleA();
        this.titleB = info.getTitleB();
    }
}
