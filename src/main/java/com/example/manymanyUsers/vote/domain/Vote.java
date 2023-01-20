package com.example.manymanyUsers.vote.domain;

import com.example.manymanyUsers.common.domain.BaseTimeEntity;
import com.example.manymanyUsers.user.domain.User;
import com.example.manymanyUsers.vote.dto.UpdateVoteRequest;
import com.example.manymanyUsers.vote.enums.Age;
import com.example.manymanyUsers.vote.enums.Category;
import com.example.manymanyUsers.vote.enums.Gender;
import com.example.manymanyUsers.vote.enums.MBTI;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Vote extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long id;

    /**
     * User 와의 연관관계 주인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User postedUser;

    /**
     * voteResult 와의 연관관계 주인
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOTE_RESULT_ID")
    private VoteResult voteResult;

    @Column
    private String totalTitle;

    @Column
    private String imageA;

    @Column
    private String imageB;

    @Column
    private String titleA;

    @Column
    private String titleB;

    @Column
    private String detail;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender filteredGender;

    /**
     * 필터링 거는 나이는 10대, 20대, 30대 이므로 여기는 AGE enum을 이용하고
     * User 엔티티의 나이는 실제 나이를 입력받으므로 INTEGER 로 저장
     */
    @Column
    @Enumerated(EnumType.STRING)
    private Age filteredAge;

    @Column
    @Enumerated(EnumType.STRING)
    private MBTI filteredMbti;

    @Builder
    public Vote(User postedUser, String totalTitle, String imageA, String imageB, String titleA, String titleB, String detail, Category category, Gender filteredGender, Age filteredAge, MBTI filteredMbti) {
        this.postedUser = postedUser;
        this.totalTitle = totalTitle;
        this.imageA = imageA;
        this.imageB = imageB;
        this.titleA = titleA;
        this.titleB = titleB;
        this.detail = detail;
        this.category = category;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
    }

    public void update(UpdateVoteRequest updateVoteRequest) {
        this.totalTitle = updateVoteRequest.getTitle();
        this.imageA = updateVoteRequest.getImageA();
        this.imageB = updateVoteRequest.getImageB();
        this.titleA = updateVoteRequest.getTitleA();
        this.titleB = updateVoteRequest.getTitleB();
        this.detail = updateVoteRequest.getDetail();
        this.category = updateVoteRequest.getCategory();
        this.filteredGender = updateVoteRequest.getFilteredGender();
        this.filteredAge = updateVoteRequest.getFilteredAge();
        this.filteredMbti = updateVoteRequest.getFilteredMbti();
    }
}
