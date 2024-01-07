package org.sopt.lequuServer.domain.common.dto.response;

public record SplashDto(Long noteNum) {
    public static SplashDto of(Long noteNum) {
        return new SplashDto(
                noteNum
        );
    }
}
