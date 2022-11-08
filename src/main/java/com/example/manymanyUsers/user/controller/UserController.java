package com.example.manymanyUsers.user.controller;

import com.example.manymanyUsers.user.dto.SignUpRequestDto;
import com.example.manymanyUsers.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

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
