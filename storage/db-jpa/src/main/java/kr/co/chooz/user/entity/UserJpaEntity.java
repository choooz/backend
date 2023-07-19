package kr.co.chooz.user.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import kr.co.chooz.user.domain.entitiy.*;
import kr.co.chooz.user.dto.AddUserCategory;
import kr.co.chooz.user.dto.AddUserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String nickname;
    private String email;
    private String imageUrl;
    private String password;
    @Enumerated(EnumType.STRING)
    private MbtiType mbti;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_category", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();

    private String providerId;  // oauth2를 이용할 경우 아이디값
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Builder
    private UserJpaEntity(String nickname, String email, String imageUrl, String password, String providerId, ProviderType providerType) {
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.providerId = providerId;
        this.providerType = providerType;
    }

    public static UserJpaEntity of(User user) {
        return UserJpaEntity.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .providerId(user.getProviderId())
                .providerType(user.getProviderType())
                .build();
    }

    public User toDomainUser() {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .password(password)
                .providerId(providerId)
                .providerType(providerType)
                .mbti(mbti)
                .gender(gender)
                .age(age)
                .categories(Categories.of(categories))
                .build();
    }

    public void addInfo(AddUserInfo addUserInfo) {
        this.mbti = addUserInfo.getMbti();
        this.age = addUserInfo.getAge();
        this.gender = addUserInfo.getGender();
    }

    public void addCategory(AddUserCategory addUserCategory) {
        this.categories = addUserCategory.getCategories();
    }

    public AgeType classifyAge(Integer age){
        if (age == null) {
            return AgeType.NULL; // 혹은 원하는 다른 동작 수행
        }
        AgeType ageGroup;
        switch (age/10){
            case 1:
                ageGroup = AgeType.teenager;
                break;
            case 2:
                ageGroup = AgeType.twenties;
                break;
            case 3:
                ageGroup = AgeType.thirties;
                break;
            case 4:
                ageGroup = AgeType.fourties;
                break;
            case 5:
                ageGroup = AgeType.fifties;
                break;
            default:
                ageGroup = AgeType.NULL;
                break;
        }
        return ageGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserJpaEntity)) return false;
        UserJpaEntity that = (UserJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}