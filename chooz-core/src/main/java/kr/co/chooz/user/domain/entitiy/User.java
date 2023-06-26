package kr.co.chooz.user.domain.entitiy;


import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String nickName;
    private String email;
    private String password;
    private String providerId;
    private ProviderType providerType;
    private RoleType role;
    private Integer age;
    private GenderType gender;
    private MbtiType mbti;


    public User(String nickName, String email, String password, String providerId, ProviderType providerType) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.providerId = providerId;
        this.providerType = providerType;
    }

    public User(String providerId, ProviderType providerType) {

        validateSignupInfo(providerId, providerType);

        this.providerId = providerId;
        this.providerType = providerType;
    }

    private void validateSignupInfo(String providerId, ProviderType providerType) {
        if (providerId == null) {
            throw new IllegalArgumentException("ProviderId는 null이면 안됩니다.");
        }
        if (providerType == null) {
            throw new IllegalArgumentException("ProviderType은 null이면 안됩니다.");
        }
    }


}
