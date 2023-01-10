package com.example.manymanyUsers.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddNewInfoResponse {
    private boolean isNewUser;

    public AddNewInfoResponse(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
