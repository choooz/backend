package kr.co.chooz.vote.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.chooz.user.domain.entitiy.Category;
import kr.co.chooz.vote.dto.UpdateVoteInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    private String title;

    @Schema(description = "투표 상세글")
    private String detail;

    @Schema(description = "투표 카테고리")
    private Category category;

    @Schema(description = "A 항목의 제목")
    private String titleA;

    @Schema(description = "B 항목의 제목")
    private String titleB;

    @Builder
    public UpdateVoteRequest(String title, String detail, Category category, String titleA, String titleB) {
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.titleA = titleA;
        this.titleB = titleB;
    }

    public UpdateVoteInfo toDomain(Long userId, Long voteId) {

        return new UpdateVoteInfo(userId, voteId, title, detail, category, titleA, titleB);

    }
}
