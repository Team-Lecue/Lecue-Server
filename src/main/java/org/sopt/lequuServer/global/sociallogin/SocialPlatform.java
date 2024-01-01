package org.sopt.lequuServer.global.sociallogin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialPlatform {
    KAKAO("카카오"),
    ;

    private final String value;
}
