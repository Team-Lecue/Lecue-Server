package org.sopt.lequuServer.global.sociallogin.kakao;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.global.sociallogin.kakao.fegin.KakaoApiClient;
import org.sopt.lequuServer.global.sociallogin.kakao.fegin.KakaoAuthApiClient;
import org.sopt.lequuServer.global.sociallogin.kakao.response.KakaoAccessTokenResponse;
import org.sopt.lequuServer.global.sociallogin.kakao.response.KakaoUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginService {

    @Value("${kakao.client-id}")
    private String CLIENT_ID;
    @Value("${kakao.authorization-grant-type}")
    private String GRANT_TYPE;
    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URL;

    private final KakaoAuthApiClient kakaoAuthApiClient;
    private final KakaoApiClient kakaoApiClient;

    public String getKakaoAccessToken(String code) {

        // Authorization code로 카카오 Access 토큰 불러오기
        KakaoAccessTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
                GRANT_TYPE,
                CLIENT_ID,
                REDIRECT_URL,
                code
        );
        return tokenResponse.getAccessToken();
        // 카카오 Refresh 토큰은 미사용
    }

    public String getKakaoId(String socialAccessToken) {

        // 카카오 Access 토큰으로 유저 정보 불러오기
        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation("Bearer " + socialAccessToken);

        return Long.toString(userResponse.getId());
    }

    public void setKakaoInfo(User loginUser, String socialAccessToken) {

        // 카카오 Access 토큰으로 유저 정보 불러오기
        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation("Bearer " + socialAccessToken);

        loginUser.updateSocialInfo(userResponse.getKakaoAccount().getProfile().getNickname(),
                userResponse.getKakaoAccount().getProfile().getProfileImageUrl(),
                socialAccessToken); //카카오 Access 토큰도 매번 업데이트
    }

}