package kr.co.chooz.user.entity;


import lombok.Builder;

@Builder
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


    public User(String name, String email, String password, String providerId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.providerId = providerId;
    }

    public User(GeneralSignupInfo signupInfo) {
        this.name = signupInfo.getName();
        this.email = signupInfo.getEmail();
        this.password = signupInfo.getPassword();
    }

    public User(String providerId, ProviderType providerType) {
        this.providerId = providerId;
        this.provider = providerType;
    }
}
