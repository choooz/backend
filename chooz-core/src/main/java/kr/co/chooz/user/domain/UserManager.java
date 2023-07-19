package kr.co.chooz.user.domain;

import kr.co.chooz.user.dto.AddUserCategory;
import kr.co.chooz.user.dto.AddUserInfo;
import kr.co.chooz.user.port.out.UserStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserStoreRepository userStoreRepository;

    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        userStoreRepository.addUserInfo(userId, addUserInfo);
    }

    public void addUserCategory(Long userId, AddUserCategory addUserCategory) {
        userStoreRepository.addCategory(userId, addUserCategory);
    }
}
