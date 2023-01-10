package com.example.manymanyUsers.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddNewInfoResponse {
    private boolean isNewUser;
    private String message;

    @Builder
    public AddNewInfoResponse(boolean isNewUser, String message) {
        this.isNewUser = isNewUser;
        this.message = message;
    }
}
