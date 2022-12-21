package com.example.manymanyUsers.user.controller;

import com.example.manymanyUsers.user.dto.SignUpRequest;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.vote.dto.CreateVoteResponse;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpRequest signUpRequestDto) {
        try {
          userService.registerUser(signUpRequestDto);
        } catch (Exception e) {
            CreateVoteResponse response = new CreateVoteResponse("조건을 만족하는 유저가 이미 존재합니다. 다시한번 확인하세요");
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }

        CreateVoteResponse response = new CreateVoteResponse("회원가입에 성공했습니다.");
        return new ResponseEntity(response, HttpStatus.OK);
    }






}
