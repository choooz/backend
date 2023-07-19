package kr.co.chooz.vote.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteContentJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "VOTE_CONTENT_ID")
    private Long id;

    private String imageA;

    private String imageB;

    private String titleA;

    private String titleB;

    @Builder
    public VoteContentJpaEntity(String imageA, String imageB, String titleA, String titleB) {
        this.imageA = imageA;
        this.imageB = imageB;
        this.titleA = titleA;
        this.titleB = titleB;
    }

    public static VoteContentJpaEntity of(VoteContent voteContent) {
        return VoteContentJpaEntity.builder()
                .imageA(voteContent.getImageA())
                .imageB(voteContent.getImageB())
                .titleA(voteContent.getTitleA())
                .titleB(voteContent.getTitleB())
                .build();
    }



}
