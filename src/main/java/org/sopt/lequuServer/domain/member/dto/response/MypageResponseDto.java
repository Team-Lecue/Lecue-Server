package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MypageResponseDto(

    @Schema(description = "유저 닉네임", example = "레큐")
    String memberNickname
) {
    public static MypageResponseDto of(String nickName) {
        return new MypageResponseDto(nickName);
    }
}
