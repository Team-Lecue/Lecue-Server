package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginResponseDto {

    @Schema(description = "유저 id", example = "1")
    private Long memberId;

    @Schema(description = "유저 닉네임", example = "레큐")
    private String nickname;

    @Schema(description = "엑세스 토큰 + 리프레시 토큰 ")
    private TokenDto tokenDto;

    @Schema(description = "소셜 로그인 플랫폼", example = "KAKAO")
    private SocialPlatform socialPlatform;

    @Schema(description = "소셜 닉네임", example = "레큐")
    private String socialNickname;

    @Schema(description = "소셜 프로필 이미지", example = "http://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg")
    private String socialProfileImage;

    public static MemberLoginResponseDto of(Member loginMember, TokenDto tokenDto) {

        return new MemberLoginResponseDto(
                loginMember.getId(), loginMember.getNickname(), tokenDto,
                loginMember.getSocialPlatform(), loginMember.getSocialNickname(), loginMember.getSocialProfileImage()
        );
    }
}
