package com.example.manymanyUsers.vote.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetVoteRecommendListResponse {

    private List<String> recommendKeywords;

    GetVoteRecommendListResponse(List<String> recommendKeywords) {
        this.recommendKeywords = recommendKeywords;
    }

}
