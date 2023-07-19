package kr.co.chooz.vote.repository;

import kr.co.chooz.vote.entity.VoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteReadJpaRepository extends JpaRepository<VoteJpaEntity, Long> {
}
