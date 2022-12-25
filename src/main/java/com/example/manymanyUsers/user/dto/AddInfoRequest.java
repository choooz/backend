package com.example.manymanyUsers.user.dto;

import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;


/**
 * 유저 정보 추가 기입 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddInfoRequest {

    private Long userId;

    private MBTI mbti;

    private Integer age;

    private Gender gender;

}
