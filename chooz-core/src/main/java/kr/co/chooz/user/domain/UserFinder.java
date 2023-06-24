package kr.co.chooz.user.domain;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFinder {
    private final UserPersistencePort userPersistencePort;

    public User findByProviderId(String providerId) {
        return userPersistencePort.findByProviderId(providerId);
    }
}