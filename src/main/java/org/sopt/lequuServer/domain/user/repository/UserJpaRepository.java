package org.sopt.lequuServer.domain.user.repository;

import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.global.sociallogin.SocialPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);
}
