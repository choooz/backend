package kr.co.chooz.user.dto;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import lombok.Getter;

@Getter
public class SocialLoginRequest {
    private String email;
    private ProviderType providerType;
    private String providerId;

    public SocialLoginRequest(String email, ProviderType providerType, String providerId) {
        this.email = email;
        this.providerType = providerType;
        this.providerId = providerId;
    }
}