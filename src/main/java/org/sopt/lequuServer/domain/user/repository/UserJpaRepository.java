package org.sopt.lequuServer.domain.user.repository;

import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.domain.user.model.SocialPlatform;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    Optional<User> findBySocialPlatformAndSocialId(SocialPlatform socialPlatform, String socialId);

    default User findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER_ERROR)
        );
    }
}
