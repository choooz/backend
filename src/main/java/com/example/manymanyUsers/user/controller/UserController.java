package com.example.manymanyUsers.user.controller;

import com.example.manymanyUsers.user.dto.*;
import com.example.manymanyUsers.user.service.UserService;
import com.example.manymanyUsers.common.dto.CommonResponse;
import io.jsonwebtoken.Claims;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequestDto) {
        try {
            userService.registerUser(signUpRequestDto);
        } catch (Exception e) {
            CommonResponse response = new CommonResponse("조건을 만족하는 유저가 이미 존재합니다. 다시한번 확인하세요");
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }

        CommonResponse response = new CommonResponse("회원가입에 성공했습니다.");
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/addInfo")
    public ResponseEntity<AddNewInfoResponse> addUserInfo(@Valid @RequestBody AddInfoRequest addInfoRequest, @RequestAttribute Claims claims) {
        Integer userId = (int) claims.get("userId");
        Long longId = Long.valueOf(userId);
        boolean isNewUser;
        try {
             isNewUser = userService.addUserInfo(addInfoRequest, longId);
        } catch (NotFoundException e) {
            AddNewInfoResponse response = AddNewInfoResponse.builder().message("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.").build();
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        AddNewInfoResponse response = AddNewInfoResponse.builder().isNewUser(isNewUser).message("유저 정보 추가에 성공했습니다.").build();
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PatchMapping("/addInterestCategory")
    public ResponseEntity<CommonResponse> AddInterestCategory(@RequestBody AddInterestCategoryRequest addInterestCategoryRequest) {
        try {
            userService.addInterestCategory(addInterestCategoryRequest);
        } catch (NotFoundException e) {
            CommonResponse response = new CommonResponse("해당 아이디 값을 가진 유저가 없습니다. 아이디를 다시 한번 확인하세요.");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        CommonResponse response = new CommonResponse("유저 관심사 카테고리 추가에 성공했습니다.");
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
