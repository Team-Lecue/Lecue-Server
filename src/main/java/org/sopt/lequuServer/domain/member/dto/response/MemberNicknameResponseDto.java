package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberNicknameResponseDto(

        @Schema(description = "유저 id", example = "1")
        Long memberId
) {
    public static MemberNicknameResponseDto of(Long memberId) {
        return new MemberNicknameResponseDto(memberId);
    }
}