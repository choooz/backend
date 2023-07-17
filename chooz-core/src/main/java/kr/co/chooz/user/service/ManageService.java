package kr.co.chooz.user.service;

import kr.co.chooz.user.domain.UserManager;
import kr.co.chooz.user.dto.AddUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageService {

    private final UserManager userManager;

    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        userManager.addUserInfo(userId, addUserInfo);
    }

}
