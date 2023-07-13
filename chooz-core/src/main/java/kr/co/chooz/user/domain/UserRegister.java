package kr.co.chooz.user.domain;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRegister {
    private final UserPersistencePort userPersistencePort;

    /**
     * 일반 회원 가입
     */
    public void register(String providerId, ProviderType providerType) {
        if (userPersistencePort.existsByProviderId(providerId)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User(providerId, providerType);
        userPersistencePort.register(user);
    }

    /**
     * 소셜 회원 가입
     */
    public boolean registerIfNeed(String providerId, ProviderType providerType) {
        if (userPersistencePort.existsByProviderId(providerId)) {
            return false;
        }
        User user = new User(providerId, providerType);
        userPersistencePort.register(user);
        return true;
    }
}
