package kr.co.chooz.user.domain.validate;

import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RegisterUserValidator {

    private final UserPersistencePort userPersistencePort;

    public boolean isDuplicatedUser(String providerId) {
        return userPersistencePort.isUserExistByProviderId(providerId);
    }

    public boolean isDuplicatedEmail() {}

}
