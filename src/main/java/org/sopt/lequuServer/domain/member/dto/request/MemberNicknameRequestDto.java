package org.sopt.lequuServer.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberNicknameRequestDto(
        @NotBlank
        @Size(min = 2, max = 8, message = "닉네임은 2글자 이상 8글자 이하여야합니다.")
        String nickname
) {
}