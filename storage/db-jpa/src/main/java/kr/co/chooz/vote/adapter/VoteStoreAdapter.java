package kr.co.chooz.vote.adapter;

import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.repository.UserJpaRepository;
import kr.co.chooz.vote.dto.UpdateVoteInfo;
import kr.co.chooz.vote.entity.*;
import kr.co.chooz.vote.port.out.VoteStoreRepository;
import kr.co.chooz.vote.repository.VoteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VoteStoreAdapter implements VoteStoreRepository {

    private final VoteJpaRepository voteJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Long register(Vote vote, VoteContent voteContent, VoteFilter voteFilter, Long userId) {

        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow(RuntimeException::new);

        VoteContentJpaEntity voteContentJpaEntity = VoteContentJpaEntity.of(voteContent);
        VoteFilterJpaEntity voteFilterJpaEntity = VoteFilterJpaEntity.of(voteFilter);
        VoteJpaEntity voteJpaEntity = VoteJpaEntity.of(vote, voteContentJpaEntity, voteFilterJpaEntity, userJpaEntity);

        Long id = voteJpaRepository.save(voteJpaEntity).getId();

        return id;
    }

    @Override
    public void update(UpdateVoteInfo info) {

        VoteJpaEntity voteJpaEntity = voteJpaRepository.findById(info.getVoteId()).orElseThrow(RuntimeException::new);
        voteJpaEntity.update(info);

    }

    @Override
    public void delete(Long voteId) {

        VoteJpaEntity voteJpaEntity = voteJpaRepository.findById(voteId).orElseThrow(RuntimeException::new);

        voteJpaRepository.deleteById(voteId);

    }
}
