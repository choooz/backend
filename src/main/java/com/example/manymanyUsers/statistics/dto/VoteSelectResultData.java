package com.example.manymanyUsers.statistics.dto;

import lombok.Getter;

@Getter
public class VoteSelectResultData {

    private int totalCountA;

    private int totalCountB;

    private int percentA;

    private int percentB;

    public VoteSelectResultData(int totalCountA, int totalCountB, int percentA, int percentB) {
        this.totalCountA = totalCountA;
        this.totalCountB = totalCountB;
        this.percentA = percentA;
        this.percentB = percentB;
    }
}
