package org.sopt.lequuServer.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberNicknameRequestDto(

        @Schema(example = "레큐")
        @NotBlank
        @Size(min = 2, max = 8, message = "닉네임은 2글자 이상 8글자 이하여야합니다.")
        @Pattern(regexp = "^[A-Za-z0-9가-힣\\s]+$", message = "닉네임은 한글, 영문 대소문자, 숫자만 가능합니다.")
        String nickname
) {
}