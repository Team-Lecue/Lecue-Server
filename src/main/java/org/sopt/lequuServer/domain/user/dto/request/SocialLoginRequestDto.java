package org.sopt.lequuServer.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.domain.user.model.SocialPlatform;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "소셜 로그인 DTO")
public class SocialLoginRequestDto {

    @Schema(description = "소셜 플랫폼 (KAKAO 등)")
    private SocialPlatform socialPlatform;
}