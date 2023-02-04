package com.example.manymanyUsers.comment.dto;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {

    private String content;

}
