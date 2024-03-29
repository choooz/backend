package com.example.manymanyUsers.comment.service;


import com.example.manymanyUsers.comment.domain.Comment;
import com.example.manymanyUsers.comment.domain.CommentEmotion;
import com.example.manymanyUsers.comment.dto.CommentCreateRequest;
import com.example.manymanyUsers.comment.dto.CommentListWithCount;
import com.example.manymanyUsers.comment.dto.CommentUpdateRequest;
import com.example.manymanyUsers.comment.enums.CommentSortBy;
import com.example.manymanyUsers.comment.enums.Emotion;
import com.example.manymanyUsers.comment.repository.CommentEmotionRepository;
import com.example.manymanyUsers.comment.repository.CommentRepository;
import com.example.manymanyUsers.exception.comment.CommentNotFoundException;
import com.example.manymanyUsers.exception.user.UserNotFoundException;
import com.example.manymanyUsers.exception.vote.VoteNotFoundException;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.user.domain.UserRepository;
import com.example.manymanyUsers.vote.domain.Vote;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import com.example.manymanyUsers.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentEmotionRepository commentEmotionRepository;
    private final VoteRepository voteRepository;


    public void createComment(Long voteId, Long parentId, String content, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment parentComment = null;

        //대댓글 이면
        if (parentId != null) {
            parentComment = commentRepository.findById(parentId).orElseThrow(CommentNotFoundException::new);
        }

        Comment comment = Comment.builder()
                .voteId(voteId)
                .content(content)
                .commentUser(user)
                .mbti(user.getMbti())
                .gender(user.getGender())
                .age(user.classifyAge(user.getAge()))
                .build();
        comment.updateParent(parentComment);
        commentRepository.save(comment);
    }


    public CommentListWithCount getComments(Long voteId, Gender gender, Age age, MBTI mbti, int size, int page, CommentSortBy sortBy) throws VoteNotFoundException {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Pageable pageable = PageRequest.of(page, size);

        List<Comment> comments = new ArrayList<>(); //댓글
        List<Comment> childComments = new ArrayList<>(); //대댓글
        int countComments = 0;

        if (CommentSortBy.ByPopularity == sortBy) {
            //인기순
            comments = commentRepository.findHotComments(voteId, gender, age, mbti, pageable);
            countComments = commentRepository.countHotComments(voteId, gender, age, mbti);

        } else if (CommentSortBy.ByTime == sortBy) {
            //최신순
            comments = commentRepository.findNewestComments(voteId, gender, age, mbti, pageable);
            countComments = commentRepository.countNewestComments(voteId, gender, age, mbti);
        }


        //대댓글 가져오기
        for (Comment parentComment : comments) {
            childComments.addAll(commentRepository.findChildComments(voteId, gender, age, mbti, parentComment));
        }

        comments.addAll(childComments);

        return new CommentListWithCount(comments, countComments);
    }

    public List<Comment> getHotComments(Long voteId, Gender gender, Age age, MBTI mbti) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment topComment = commentRepository.findHotComments(voteId, gender, age, mbti, PageRequest.of(0, 1)).get(0);
        List<Comment> newestComment = commentRepository.findNewestComments(voteId, gender, age, mbti, PageRequest.of(0, 3));

        List<Comment> hotComments = new ArrayList<>();
        hotComments.add(topComment);

        for (Comment comment : newestComment) {
            if (!comment.equals(topComment)) {
                hotComments.add(comment);
            }
            if (hotComments.size() == 3) {
                break;
            }
        }

        return hotComments;
    }


    public void updateComment(Long voteId, Long commentId, Long userId, CommentUpdateRequest commentUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.update(commentUpdateRequest);
    }

    public void deleteComment(Long voteId, Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);


        commentRepository.deleteById(commentId);
    }


    public void emoteComment(Long voteId, Long commentId, Long userId, Emotion emotion) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        Optional<CommentEmotion> byCommentAndUser = commentEmotionRepository.findByCommentAndUser(comment, user);

        byCommentAndUser.ifPresentOrElse(commentEmotion -> {

                    //좋아요(싫어요)를 기존에 눌렀는데 또 눌렀을 경우 좋아요(싫어요) 취소
                    if (emotion == commentEmotion.getEmotion()) {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);
                        comment.updateLikeHateCount();
                    }
                    //싫어요(좋아요)를 기존에 누른 상태로 좋아요(싫어요)를 누른 경우 싫어요(좋아요) 취소 후 좋아요(싫어요)로 등록
                    else {
                        commentEmotionRepository.delete(commentEmotion);
                        comment.removeEmotion(commentEmotion);

                        CommentEmotion changeEmotion = new CommentEmotion();

                        changeEmotion.setEmote(emotion);
                        changeEmotion.mappingComment(comment);
                        changeEmotion.mappingUser(user);
                        comment.updateLikeHateCount();

                        commentEmotionRepository.save(changeEmotion);
                    }

                },
                // 좋아요(싫어요)가 없을 경우 좋아요(싫어요) 추가
                () -> {
                    CommentEmotion commentEmotion = new CommentEmotion();

                    commentEmotion.setEmote(emotion);
                    commentEmotion.mappingComment(comment);
                    commentEmotion.mappingUser(user);
                    comment.updateLikeHateCount();

                    commentEmotionRepository.save(commentEmotion);
                });
    }


    public Long getCommentsCountByVote(Long voteId) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        return commentRepository.countCommentsByVoteId(voteId);
    }




}
