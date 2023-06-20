package kr.co.chooz.user.adapter;

import kr.co.chooz.user.UserRepository;
import kr.co.chooz.user.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;


    @Override
    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }
}
