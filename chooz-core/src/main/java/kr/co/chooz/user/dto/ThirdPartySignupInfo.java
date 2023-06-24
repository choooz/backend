package kr.co.chooz.user.dto;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import lombok.Getter;

import java.util.Map;

@Getter
public class ThirdPartySignupInfo {
    private ProviderType providerType;
    private Map<String, String> propertiesValues;

    public ThirdPartySignupInfo(ProviderType providerType, Map<String, String> propertiesValues) {
        this.providerType = providerType;
        this.propertiesValues = propertiesValues;
    }
}