package com.example.manymanyUsers.vote.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor
public class GetVoteListResponse {

    private Slice<VoteListData> voteSlice;

    private String message;

    @Builder
    public GetVoteListResponse(Slice<VoteListData> voteSlice, String message) {
        this.voteSlice = voteSlice;
        this.message = message;
    }
}
