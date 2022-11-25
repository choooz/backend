package com.example.manymanyUsers.user.service;

import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.user.dto.SignUpRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long registerUser(SignUpRequest signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
//            throw new
        }

        User user = new User();
        user.setUsername(signUpRequestDto.getName());
        user.setEmail(signUpRequestDto.getEmail());
        user.setPassword(signUpRequestDto.getPassword());

        User result = userRepository.save(user);
        return result.getId();
    }


}
