package org.sopt.lequuServer.domain.user.controller;

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
public class UserController {

    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserLoginResponseDto> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request) {

        return ApiResponse.success(LOGIN_SUCCESS, userService.login(socialAccessToken, request));
    }

    @GetMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TokenDto> reissue(
            @RequestHeader("Authorization") String refreshToken) {

        return ApiResponse.success(REISSUE_SUCCESS, userService.reissueToken(refreshToken));
    }

    @PatchMapping("/log-out") // Spring Security 자체의 logout과 겹치지 않기 위해 이렇게 설정
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> logout(Principal principal) {

        userService.logout(JwtProvider.getUserFromPrincial(principal));
        return ApiResponse.success(LOGOUT_SUCCESS);
    }

    @GetMapping("/kakao")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> kakaoAccessToken(
            @RequestHeader("Authorization") String code) {

        return ApiResponse.success(KAKAO_ACCESS_TOKEN_SUCCESS, kakaoLoginService.getKakaoAccessToken(code));
    }
}
