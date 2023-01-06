package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.SortBy;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GetVoteListRequest {

    private SortBy sortBy;

    private int page;

    private int size;
}
