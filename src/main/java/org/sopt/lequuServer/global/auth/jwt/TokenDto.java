package org.sopt.lequuServer.global.auth.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @Schema(description = "엑세스 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDQ5NTcwNzMsImV4cCI6MTczNjQ5MzA3MywibWVtYmVySWQiOjF9.ALyJdcAKVw6uJdkTJXJ_nt-tycbtCTM3Gfw5Ko3adfUE4NCqTaB9K9Y9daihkQ3hKXZK8XTUwRHFKDTsLDgPTw")
    private String accessToken;

    @Schema(description = "리프레시 토큰 ", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2ODkyNzA1NDgsImV4cCI6MTY4OTI4MTM0OH0.e1rHHRLwWRSPZ6LhWBRwqzvcSJN3f-KTyKFqNt0uAvJhnlsaUB_qS0ApMhGP_JdH3e9vNNRq1RXgayLMw76cIA")
    private String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }
}