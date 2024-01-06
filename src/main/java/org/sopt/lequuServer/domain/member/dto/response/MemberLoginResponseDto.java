package org.sopt.lequuServer.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginResponseDto {
    private Long memberId;

    private String nickname;

    private TokenDto tokenDto;

    private SocialPlatform socialPlatform;

    private String socialNickname;

    private String socialProfileImage;

    public static MemberLoginResponseDto of(Member loginMember, TokenDto tokenDto) {

        return new MemberLoginResponseDto(
                loginMember.getId(), loginMember.getNickname(), tokenDto,
                loginMember.getSocialPlatform(), loginMember.getSocialNickname(), loginMember.getSocialProfileImage()
        );
    }
}
