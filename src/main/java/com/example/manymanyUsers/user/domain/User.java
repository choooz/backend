package com.example.manymanyUsers.user.domain;

import com.example.manymanyUsers.comment.domain.CommentEmotion;
import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.enums.Providers;
import com.example.manymanyUsers.user.enums.Role;
import com.example.manymanyUsers.vote.domain.Bookmark;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String nickname;

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
    @JoinColumn(name = "USER_ID")
    private List<CategoryEntity> categoryLists = new ArrayList<>();

    @Column(name = "modified_MBTI_Date")
    private LocalDateTime modifiedMBTIDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommentEmotion> commentEmotionList = new ArrayList<>();


    public void mappingCommentLike(CommentEmotion commentEmotion) {
        this.commentEmotionList.add(commentEmotion);
    }

    public void mappingBookmark(Bookmark bookmark) {
        this.bookmarkList.add(bookmark);
    }

    public void updateProfile(String nickname, String image, MBTI mbti, LocalDateTime modifiedMBTIDate) {
        this.nickname = nickname;
        this.imageUrl = image;
        this.mbti = mbti;
        this.modifiedMBTIDate = modifiedMBTIDate;
    }

    @Builder
    public User(Long id, String nickname, String email, String imageUrl, String password, Providers provider, String providerId, Role role, Integer age, Gender gender, MBTI mbti, List<CategoryEntity> categoryLists, LocalDateTime modifiedMBTIDate) {
        this.id = id;
        this.nickname = nickname;
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
        this.modifiedMBTIDate = modifiedMBTIDate;
    }

    public Age classifyAge(Integer age){
        Age ageGroup;
        switch (age/10){
            case 1:
                ageGroup = Age.teenager;
                break;
            case 2:
                ageGroup = Age.twenties;
                break;
            case 3:
                ageGroup = Age.thirties;
                break;
            case 4:
                ageGroup = Age.fourties;
                break;
            case 5:
                ageGroup = Age.fifties;
                break;
            default:
                ageGroup = Age.NULL;
                break;
        }
        return ageGroup;
    }
}
