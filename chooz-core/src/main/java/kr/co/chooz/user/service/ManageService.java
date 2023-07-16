package kr.co.chooz.user.service;

import kr.co.chooz.user.domain.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageService {

    private final UserManager userManager;

    public void addUserInfo(Long userId) {

    }

}
