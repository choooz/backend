package kr.co.chooz.vote.adapter;

import kr.co.chooz.user.entity.UserJpaEntity;
import kr.co.chooz.user.repository.UserJpaRepository;
import kr.co.chooz.vote.entity.*;
import kr.co.chooz.vote.port.out.VoteStoreRepository;
import kr.co.chooz.vote.repository.VoteStoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VoteStoreAdapter implements VoteStoreRepository {

    private final VoteStoreJpaRepository voteStoreJpaRepository;
    private final UserJpaRepository userReadRepository;

    @Override
    public Long register(Vote vote, VoteContent voteContent, VoteFilter voteFilter, Long userId) {

        Optional<UserJpaEntity> userJpaEntity = userReadRepository.findById(userId);

        UserJpaEntity user = userJpaEntity.get();

        VoteContentJpaEntity voteContentJpaEntity = VoteContentJpaEntity.of(voteContent);
        VoteFilterJpaEntity voteFilterJpaEntity = VoteFilterJpaEntity.of(voteFilter);
        VoteJpaEntity voteJpaEntity = VoteJpaEntity.of(vote, voteContentJpaEntity, voteFilterJpaEntity, user);

        Long id = voteStoreJpaRepository.save(voteJpaEntity).getId();

        return id;
    }
}
