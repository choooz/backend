package kr.co.chooz.user.domain.entitiy;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String nickname;
    private String email;
    private String password;
    private RoleType role;
    private Integer age;
    private GenderType gender;
    private MbtiType mbti;
    private String providerId;
    private ProviderType providerType;


    @Builder
    private User(Long id, String nickname, String email, String password, String providerId, ProviderType providerType, RoleType role, Integer age, GenderType gender, MbtiType mbti) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.providerId = providerId;
        this.providerType = providerType;
        this.role = role;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
    }

}
