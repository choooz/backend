package com.example.manymanyUsers.comment.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDeleteRequest {

    private Long userId;

}
