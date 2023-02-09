package com.example.manymanyUsers.comment.dto;


import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentGetResponse {

    private Long id;

    private Long userId;

    private Long parentId;

    private String content;

    private Gender gender;

    private String imageUrl;

    private Age age;

    private MBTI mbti;

    private String nickName;

    private LocalDateTime createdDate;

    private Long likeCount;


    private Long hateCount;

    private List<CommentGetResponse> children = new ArrayList<>();



}
