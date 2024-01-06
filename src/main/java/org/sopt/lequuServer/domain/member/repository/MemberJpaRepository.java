package org.sopt.lequuServer.domain.member.repository;

import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    Optional<Member> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
}
