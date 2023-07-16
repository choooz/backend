package kr.co.chooz.user.domain;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.port.out.UserReadRepository;
import kr.co.chooz.user.port.out.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegister {
    private final UserStoreRepository userStoreRepository;
    private final UserReadRepository userReadRepository;

    /**
     * 일반 회원 가입
     */
    public void register(String providerId, ProviderType providerType) {
        if (userReadRepository.existsByProviderId(providerId)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        User user = userOfWhenLogin(providerId, providerType);
        userStoreRepository.register(user);
    }

    /**
     * 소셜 회원 가입
     */
    public boolean registerIfNeed(String providerId, ProviderType providerType) {
        if (userReadRepository.existsByProviderId(providerId)) {
            return false;
        }
        User user = userOfWhenLogin(providerId, providerType);
        userStoreRepository.register(user);
        return true;
    }

    private User userOfWhenLogin(String providerId, ProviderType providerType) {
        return User.builder()
                .providerId(providerId)
                .providerType(providerType)
                .build();
    }
}
