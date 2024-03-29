package com.example.manymanyUsers.comment.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    private Long parentId;

    private String content;

}
