package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.domain.Vote;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor
public class VoteResponse {

    private Slice<VoteListData> voteSlice;

    private String message;

    @Builder
    public VoteResponse(Slice<VoteListData> voteSlice, String message) {
        this.voteSlice = voteSlice;
        this.message = message;
    }
}
