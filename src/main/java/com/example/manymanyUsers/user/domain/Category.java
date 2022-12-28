package com.example.manymanyUsers.user.domain;

import com.example.manymanyUsers.vote.enums.CategoryList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private CategoryList categoryList;

}
