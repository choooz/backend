package com.example.manymanyUsers.user.controller;

import com.example.manymanyUsers.user.dto.SignUpRequestDto;
import com.example.manymanyUsers.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        Long userId = userService.registerUser(signUpRequestDto);
        return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
    }
}
