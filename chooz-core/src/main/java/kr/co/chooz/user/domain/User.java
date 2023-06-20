package kr.co.chooz.user.domain;


import kr.co.chooz.user.dto.GeneralSignupInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String providerId;
    private ProviderType provider;
    private RoleType role;
    private Integer age;
    private GenderType gender;
    private MbtiType mbti;


//    public User(String name, String email, String password, String providerId) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.providerId = providerId;
//    }

    public User(GeneralSignupInfo signupInfo) {

        validateSignupInfo(signupInfo);

        this.provider = signupInfo.getProviderType();
        this.email = signupInfo.getEmail();
        this.providerId = signupInfo.getProviderId();
    }

    private void validateSignupInfo(GeneralSignupInfo signupInfo) {
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
