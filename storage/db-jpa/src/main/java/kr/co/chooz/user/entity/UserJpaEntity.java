package kr.co.chooz.user.entity;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserJpaEntity {
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


    private String providerId;  // oauth2를 이용할 경우 아이디값

    private ProviderType providerType;


    public User toDomainUser() {
        return new User(nickname, email, password, providerId, providerType);
    }


    public UserJpaEntity(User user) {
        this.nickname = user.getNickName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.providerId = user.getProviderId();
        this.providerType = user.getProviderType();
    }

}