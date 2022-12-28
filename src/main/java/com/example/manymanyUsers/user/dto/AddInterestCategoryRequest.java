package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.vote.enums.CategoryList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AddInterestCategoryRequest {

    private Long userId;

    private List<CategoryList> categoryLists = new ArrayList<>();
}
