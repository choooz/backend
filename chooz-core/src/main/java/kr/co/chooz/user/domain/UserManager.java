package kr.co.chooz.user.domain;

import kr.co.chooz.user.port.out.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserStoreRepository userStoreRepository;

}
