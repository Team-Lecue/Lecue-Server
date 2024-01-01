package org.sopt.lequuServer.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.global.sociallogin.SocialPlatform;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialLoginRequestDto {

    private SocialPlatform socialPlatform;
}