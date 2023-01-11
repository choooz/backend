package com.example.manymanyUsers.comment.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    private Long voteId;

    private Long userId;

    private String content;


//    public Comment toEntity(){
//        return Comment.builder()
//                .content(this.content)
//                .voteId(this.voteId)
//                .
//                .build();
//    }
}
