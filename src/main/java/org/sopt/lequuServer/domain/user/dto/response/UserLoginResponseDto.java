package org.sopt.lequuServer.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.sociallogin.SocialPlatform;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {
    private Long userId;

    private String nickname;

    private TokenDto tokenDto;

    private SocialPlatform socialPlatform;

    private String socialNickname;

    private String socialProfileImage;

    private String socialAccessToken;

    public static UserLoginResponseDto of(User loginUser, TokenDto tokenDto) {

        return new UserLoginResponseDto(
                loginUser.getId(), loginUser.getNickname(), tokenDto,
                loginUser.getSocialPlatform(), loginUser.getSocialNickname(), loginUser.getSocialProfileImage(),
                loginUser.getSocialAccessToken());
    }
}
