package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.SortBy;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class getVoteListRequest {

    private SortBy sortBy;
}
