package kr.co.chooz.vote.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.vote.dto.UpdateVoteInfo;
import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.dto.VotedUserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserJpaEntity postedUser;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<VoteResultJpaEntity> voteResultList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "VOTE_FILTER_ID")
    private VoteFilterJpaEntity voteFilter;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "VOTE_CONTENT_ID")
    private VoteContentJpaEntity voteContent;

    private String title;

    private String detail;

    @Builder
    public VoteJpaEntity(UserJpaEntity postedUser, List<VoteResultJpaEntity> voteResultList, VoteFilterJpaEntity voteFilter, VoteContentJpaEntity voteContent, String title, String detail) {
        this.postedUser = postedUser;
        this.voteResultList = voteResultList;
        this.voteFilter = voteFilter;
        this.voteContent = voteContent;
        this.title = title;
        this.detail = detail;
    }

    public static VoteJpaEntity of(Vote vote, VoteContentJpaEntity voteContentJpaEntity, VoteFilterJpaEntity voteFilterJpaEntity, UserJpaEntity user) {
        return VoteJpaEntity.builder()
                .postedUser(user)
                .voteContent(voteContentJpaEntity)
                .title(vote.getTitle())
                .detail(vote.getDetail())
                .voteFilter(voteFilterJpaEntity)
                .build();
    }

    public VoteInfo toDomain() {
        VotedUserInfo votedUserInfo = VotedUserInfo.builder()
                .userImage(postedUser.getImageUrl())
                .userGender(postedUser.getGender())
                .userAge(postedUser.classifyAge(postedUser.getAge()))
                .userMbti(postedUser.getMbti())
                .nickName(postedUser.getNickname())
                .build();

        return VoteInfo.builder()
                .writer(votedUserInfo)
                .voteCreatedDate(getCreatedDate())
                .category(null)
                .title(title)
                .imageA(voteContent.getImageA())
                .imageB(voteContent.getImageB())
                .filteredGender(voteFilter.getFilteredGender())
                .filteredAge(voteFilter.getFilteredAge())
                .filteredMbti(voteFilter.getFilteredMbti())
                .titleA(voteContent.getTitleA())
                .titleB(voteContent.getTitleB())
                .description(detail)
                .build();
    }

    public boolean isUserVote(Long userId) {

        return this.postedUser.getId().equals(userId);

    }


    public void update(UpdateVoteInfo info) {

        this.title = info.getTitle();
        this.detail = info.getDetail();
//        this.category = info.getCategory();
        this.voteContent.update(info.getTitleA(), info.getTitleB());

    }
}
