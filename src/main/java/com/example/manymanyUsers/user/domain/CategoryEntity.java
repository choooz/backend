package com.example.manymanyUsers.user.domain;

import com.example.manymanyUsers.vote.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryEntity {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Category categoryList;

}
