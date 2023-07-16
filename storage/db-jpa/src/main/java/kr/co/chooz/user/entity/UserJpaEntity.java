package kr.co.chooz.user.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserJpaEntity)) return false;
        UserJpaEntity that = (UserJpaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nickname, that.nickname) && Objects.equals(email, that.email) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(password, that.password) && Objects.equals(providerId, that.providerId) && providerType == that.providerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, imageUrl, password, providerId, providerType);
    }
}