package kr.co.chooz.user.domain.entitiy;


import kr.co.chooz.user.dto.LoginRequest;
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

    public User(LoginRequest signupInfo) {

        validateSignupInfo(signupInfo);

        this.providerType = signupInfo.getProviderType();
        this.email = signupInfo.getEmail();
        this.providerId = signupInfo.getProviderId();
    }

    private void validateSignupInfo(LoginRequest signupInfo) {
        if (signupInfo.getProviderId() == null) {
            throw new IllegalArgumentException("ProviderId는 null이면 안됩니다.");
        }
        if (signupInfo.getProviderType() == null) {
            throw new IllegalArgumentException("ProviderType은 null이면 안됩니다.");
        }
        if (signupInfo.getEmail() == null) {
            throw new IllegalArgumentException("Email은 null이면 안됩니다.");
        }

    }

//    public User(String providerId, ProviderType providerType) {
//        this.providerId = providerId;
//        this.provider = providerType;
//    }



}
