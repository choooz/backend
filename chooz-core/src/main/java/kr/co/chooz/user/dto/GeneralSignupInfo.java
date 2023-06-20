package kr.co.chooz.user.dto;

import kr.co.chooz.user.domain.ProviderType;
import lombok.Getter;

@Getter
public class GeneralSignupInfo {
    private String email;
    private ProviderType providerType;
    private String providerId;

    public GeneralSignupInfo(String email, ProviderType providerType, String providerId) {
        this.email = email;
        this.providerType = providerType;
        this.providerId = providerId;
    }
}