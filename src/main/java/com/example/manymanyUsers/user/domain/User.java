package com.example.manymanyUsers.user.domain;

import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.enums.Providers;
import com.example.manymanyUsers.user.enums.Role;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    private String imageUrl;

    private String password;

    @Enumerated(EnumType.STRING)
    private Providers provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지

    private String providerId;  // oauth2를 이용할 경우 아이디값

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private List<Category> categoryLists = new ArrayList<>();


    @Builder
    public User(Long id, String username, String email, String imageUrl, String password, Providers provider, String providerId, Role role, Integer age, Gender gender, MBTI mbti, List<Category> categoryLists) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
        this.categoryLists = categoryLists;
    }
}
