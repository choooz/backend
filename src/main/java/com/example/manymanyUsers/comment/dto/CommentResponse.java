package com.example.manymanyUsers.comment.dto;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;

    private Long userId;


    private Long parentId;

    private String content;

    private Gender gender;

    private String imageUrl;

    private Age age;

    private MBTI mbti;

    private String nickName;

    private LocalDateTime createdDate;  //만든 시간 말고 수정 시간을 보내줘야 하나?

    private Long likeCount;


    private List<CommentResponse> children;


    public CommentResponse(Comment c) {
        this.id = c.getId();
        this.userId = c.getCommentUser().getId();
        this.content = c.getContent();
        this.gender = c.getGender();
        this.imageUrl = c.getCommentUser().getImageUrl();
        this.age = c.getAge();
        this.mbti = c.getMbti();
        this.nickName = c.getCommentUser().getNickname();
        this.createdDate = c.getCreatedDate();
        this.likeCount = c.getLikeCount();
    }

//    public CommentResponse(Comment c) {
//        c.
//    }
}
