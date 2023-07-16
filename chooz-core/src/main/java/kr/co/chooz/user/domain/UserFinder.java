package kr.co.chooz.user.domain;

import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.port.out.UserReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFinder {
    private final UserReadRepository userReadRepository;

    public User findByProviderId(String providerId) {
        return userReadRepository.findByProviderId(providerId);
    }
}