package org.sopt.lequuServer.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.user.dto.request.SocialLoginRequestDto;
import org.sopt.lequuServer.domain.user.dto.response.UserLoginResponseDto;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.domain.user.repository.UserJpaRepository;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.auth.security.UserAuthentication;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.sopt.lequuServer.global.sociallogin.SocialPlatform;
import org.sopt.lequuServer.global.sociallogin.kakao.KakaoLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserJpaRepository userRepository;

    private final KakaoLoginService kakaoLoginService;

    @Transactional
    public UserLoginResponseDto login(String socialAccessToken, SocialLoginRequestDto request) {

        SocialPlatform socialPlatform = request.getSocialPlatform();
        String socialId = login(request.getSocialPlatform(), socialAccessToken);

        boolean isRegistered = isUserBySocialAndSocialId(socialPlatform, socialId);

        if (!isRegistered) {
            User user = User.builder()
                    .socialPlatform(socialPlatform)
                    .socialId(socialId).build();

            userRepository.save(user);
        }

        User loginUser = getUserBySocialAndSocialId(socialPlatform, socialId);

        // 카카오 로그인은 정보 더 많이 받아올 수 있으므로 추가 설정
        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.setKakaoInfo(loginUser, socialAccessToken);
        }

        if (isRegistered && loginUser.getRefreshToken() != null) {
            jwtProvider.deleteRefreshToken(loginUser.getRefreshToken());
        }
        TokenDto tokenDto = jwtProvider.issueToken(new UserAuthentication(loginUser.getId(), null, null));
        loginUser.updateRefreshToken(tokenDto.getRefreshToken());

        return UserLoginResponseDto.of(loginUser, tokenDto);
    }

    @Transactional
    public TokenDto reissueToken(String refreshToken) {

        Long userId = jwtProvider.validateRefreshToken(refreshToken);

        if (userId == -1L) {
            throw new CustomException(ErrorType.NOT_MATCH_REFRESH_TOKEN);
        }
        User user = getUserById(userId);  // userId가 DB에 저장된 유효한 값인지 검사

        jwtProvider.deleteRefreshToken(refreshToken);
        TokenDto reissuedToken =  jwtProvider.issueToken(new UserAuthentication(userId, null, null));
        user.updateRefreshToken(reissuedToken.getRefreshToken());
        return reissuedToken;
    }

    @Transactional
    public void logout(Long userId) {
        User user = getUserById(userId);
        if (user.getRefreshToken() != null) {
            jwtProvider.deleteRefreshToken(user.getRefreshToken());
        }
        user.updateRefreshToken(null);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorType.INVALID_USER));
    }

    private User getUserBySocialAndSocialId(SocialPlatform socialPlatform, String socialId) {
        return userRepository.findBySocialPlatformAndSocialId(socialPlatform, socialId)
                .orElseThrow(() -> new CustomException(ErrorType.INVALID_USER));
    }

    private boolean isUserBySocialAndSocialId(SocialPlatform socialPlatform, String socialId) {
        return userRepository.existsBySocialPlatformAndSocialId(socialPlatform, socialId);
    }

    private String login(SocialPlatform socialPlatform, String socialAccessToken) {
        switch (socialPlatform.toString()) {
            case "KAKAO":
                return kakaoLoginService.getKakaoId(socialAccessToken);
            default:
                throw new CustomException(ErrorType.INVALID_SOCIAL_ACCESS_TOKEN);
        }
    }
}