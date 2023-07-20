package kr.co.chooz.vote.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateVoteResponse {

    private Long voteId;

    public CreateVoteResponse(Long voteId) {
        this.voteId = voteId;
    }
}