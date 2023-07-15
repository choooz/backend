package kr.co.chooz.user.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    private String nickname;
    private String email;
    private String imageUrl;
    private String password;
    private String providerId;  // oauth2를 이용할 경우 아이디값

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public User toDomainUser() {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .password(password)
                .providerId(providerId)
                .providerType(providerType)
                .build();
    }

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

}