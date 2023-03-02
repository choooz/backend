package com.example.manymanyUsers.comment.dto;

import com.example.manymanyUsers.comment.enums.CommentSortBy;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import com.example.manymanyUsers.vote.enums.SortBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class CommentGetRequest {

    private Age age;

    private MBTI mbti;

    private Gender gender;

    @NotNull
    private CommentSortBy sortBy;

    @NotNull
    private int page;

    @NotNull
    private int size;
}
