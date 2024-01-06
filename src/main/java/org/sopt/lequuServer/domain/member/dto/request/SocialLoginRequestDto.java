package org.sopt.lequuServer.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialLoginRequestDto {

    private SocialPlatform socialPlatform;
}