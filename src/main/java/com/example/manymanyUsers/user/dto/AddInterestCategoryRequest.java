package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.vote.enums.CategoryList;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddInterestCategoryRequest {

    private Long userId;

    private CategoryList category;
}
