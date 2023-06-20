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
        this.provider = signupInfo.getProviderType();
        this.email = signupInfo.getEmail();
        this.providerId = signupInfo.getProviderId();
    }

//    public User(String providerId, ProviderType providerType) {
//        this.providerId = providerId;
//        this.provider = providerType;
//    }



}
