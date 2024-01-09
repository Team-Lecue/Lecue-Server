package org.sopt.lequuServer.domain.member.dto.response;

public record MemberNicknameResponseDto(Long memberId) {
    public static MemberNicknameResponseDto of(Long userId) {
        return new MemberNicknameResponseDto(userId);
    }
}