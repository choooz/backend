package kr.co.chooz.vote.adapter;

import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.entity.VoteJpaEntity;
import kr.co.chooz.vote.port.out.VoteReadRepository;
import kr.co.chooz.vote.repository.VoteReadJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteReadAdapter implements VoteReadRepository {

    private final VoteReadJpaRepository voteReadJpaRepository;

    @Override
    public VoteInfo getVote(Long voteId) {

        return voteReadJpaRepository.findById(voteId)
                .map(VoteJpaEntity::toDomain)
                .orElseThrow(RuntimeException::new);
    }
}
