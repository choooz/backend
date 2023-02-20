package com.example.manymanyUsers.comment.dto;

import com.example.manymanyUsers.comment.enums.CommentSortBy;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import com.example.manymanyUsers.vote.enums.SortBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentGetRequest {

    private Age age;

    private MBTI mbti;

    private Gender gender;

    private CommentSortBy sortBy = CommentSortBy.ByTime;

    private int page = 0;

    private int size = 5;
}
