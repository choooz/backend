package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.user.domain.CategoryEntity;
import com.example.manymanyUsers.vote.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddInterestCategoryRequest {

    private List<Category> categoryLists = new ArrayList<>();
}
