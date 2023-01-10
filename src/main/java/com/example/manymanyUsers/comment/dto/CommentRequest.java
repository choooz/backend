package com.example.manymanyUsers.comment.dto;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.FetchType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

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
