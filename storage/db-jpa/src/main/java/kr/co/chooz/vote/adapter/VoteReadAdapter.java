package kr.co.chooz.vote.adapter;

import kr.co.chooz.vote.dto.VoteInfo;
import kr.co.chooz.vote.entity.VoteJpaEntity;
import kr.co.chooz.vote.port.out.VoteReadRepository;
import kr.co.chooz.vote.repository.VoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteReadAdapter implements VoteReadRepository {

    private final VoteJpaRepository voteJpaRepository;

    @Override
    public VoteInfo getVote(Long voteId) {

        return voteJpaRepository.findById(voteId)
                .map(VoteJpaEntity::toDomain)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean isUserVote(Long userId, Long voteId) {
        VoteJpaEntity voteJpaEntity = voteJpaRepository.findById(voteId).orElseThrow(RuntimeException::new);
        return voteJpaEntity.isUserVote(userId);
    }
}
