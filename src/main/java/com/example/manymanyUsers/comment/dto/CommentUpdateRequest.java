package com.example.manymanyUsers.comment.dto;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {

    private Long userId;

    private String content;

}
