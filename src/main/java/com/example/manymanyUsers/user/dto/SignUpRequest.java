package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.user.enums.Providers;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private Providers provider;

    private String providerId;
}
