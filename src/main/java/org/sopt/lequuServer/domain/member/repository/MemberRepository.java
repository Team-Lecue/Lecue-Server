package org.sopt.lequuServer.domain.member.repository;

import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.NOT_FOUND_MEMBER_ERROR;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    Optional<Member> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    default Member findByIdOrThrow(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_ERROR));
    }
}