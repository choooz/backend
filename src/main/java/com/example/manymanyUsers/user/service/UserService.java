package com.example.manymanyUsers.user.service;

import com.example.manymanyUsers.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long registerUser() {
        if(userRepository.existsByEmail()){

        }
    }
}
