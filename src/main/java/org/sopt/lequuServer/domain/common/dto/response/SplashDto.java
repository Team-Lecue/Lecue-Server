package org.sopt.lequuServer.domain.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SplashDto(
        @Schema(description = "레큐 노트 개수", example = "1974")
        Long noteNum
) {
    public static SplashDto of(Long noteNum) {
        return new SplashDto(
                noteNum
        );
    }
}
