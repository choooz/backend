package com.example.manymanyUsers.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetBookmarkedResponse {

    private boolean bookmarked;


    public GetBookmarkedResponse(boolean bookmarked){
        this.bookmarked = bookmarked;
    }
}
