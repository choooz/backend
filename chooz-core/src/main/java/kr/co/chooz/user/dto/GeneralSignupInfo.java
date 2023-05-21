package kr.co.chooz.user.dto;

import lombok.Getter;

@Getter
public class GeneralSignupInfo {
    private String name;
    private String email;
    private String password;

    public GeneralSignupInfo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}