package com.example.manymanyUsers.vote.dto;

import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateVoteRequest {

    @NotBlank
    private String providerId;

    @NotBlank
    private String title;

    private String ImageA;

    private String ImageB;

    @NotBlank
    private String detail;

    private Gender gender;

    private Age age;

    private Category category;


}
