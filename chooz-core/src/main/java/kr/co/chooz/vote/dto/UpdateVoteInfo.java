package kr.co.chooz.vote.dto;

import kr.co.chooz.user.domain.entitiy.Category;
import lombok.Getter;

@Getter
public class UpdateVoteInfo {

    private Long userId;

    private Long voteId;

    private String title;

    private String detail;

    private Category category;

    private String titleA;

    private String titleB;

    public UpdateVoteInfo(Long userId, Long voteId, String title, String detail, Category category, String titleA, String titleB) {
        this.userId = userId;
        this.voteId = voteId;
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.titleA = titleA;
        this.titleB = titleB;
    }
}
