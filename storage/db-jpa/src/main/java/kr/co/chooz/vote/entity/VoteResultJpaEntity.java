package kr.co.chooz.vote.entity;

import kr.co.chooz.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteResultJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private VoteJpaEntity vote;

}
