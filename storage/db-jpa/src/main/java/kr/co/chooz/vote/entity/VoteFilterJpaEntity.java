package kr.co.chooz.vote.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import kr.co.chooz.user.domain.entitiy.AgeType;
import kr.co.chooz.user.domain.entitiy.GenderType;
import kr.co.chooz.user.domain.entitiy.MbtiType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteFilterJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "VOTE_FIlTER_ID")
    private Long id;

    private GenderType filteredGender;

    private AgeType filteredAge;

    private MbtiType filteredMbti;

    @Builder
    public VoteFilterJpaEntity(GenderType filteredGender, AgeType filteredAge, MbtiType filteredMbti) {
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
    }

    public static VoteFilterJpaEntity of(VoteFilter voteFilter) {
        return VoteFilterJpaEntity.builder()
                .filteredGender(voteFilter.getFilteredGender())
                .filteredAge(voteFilter.getFilteredAge())
                .filteredMbti(voteFilter.getFilteredMbti())
                .build();

    }

}
