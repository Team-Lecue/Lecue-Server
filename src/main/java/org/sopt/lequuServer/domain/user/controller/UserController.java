package org.sopt.lequuServer.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.user.dto.request.SocialLoginRequestDto;
import org.sopt.lequuServer.domain.user.dto.response.UserLoginResponseDto;
import org.sopt.lequuServer.domain.user.service.UserService;
import org.sopt.lequuServer.global.auth.fegin.kakao.KakaoLoginService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 API Document")
@SecurityRequirement(name = "JWT Auth")
public class UserController {

    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인 API", description = "유저가 서비스에 로그인 합니다. 최초 가입자라면 회원가입 합니다.")
    public ApiResponse<UserLoginResponseDto> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request) {

        return ApiResponse.success(LOGIN_SUCCESS, userService.login(socialAccessToken, request));
    }

    @GetMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Access 토큰 재발급 API", description = "Refresh 토큰을 통해 Access 토큰과 Refresh 토큰을 재발급 받습니다.")
    public ApiResponse<TokenDto> reissue(
            @RequestHeader("Authorization") String refreshToken) {

        return ApiResponse.success(REISSUE_SUCCESS, userService.reissueToken(refreshToken));
    }

    @PostMapping("/log-out") // Spring Security 자체의 logout과 겹치지 않기 위해 이렇게 설정
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그아웃 API", description = "유저가 서비스에서 로그아웃 합니다.")
    public ApiResponse<?> logout(Principal principal) {

        userService.logout(JwtProvider.getUserFromPrincial(principal));
        return ApiResponse.success(LOGOUT_SUCCESS);
    }

    @GetMapping("/kakao")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "카카오 Access 토큰 발급 API", description = "Authorization code를 통해 카카오 Access 토큰을 발급받습니다.")
    public ApiResponse<?> kakaoAccessToken(
            @RequestHeader("Authorization") String code) {

        return ApiResponse.success(KAKAO_ACCESS_TOKEN_SUCCESS, kakaoLoginService.getKakaoAccessToken(code));
    }
}
