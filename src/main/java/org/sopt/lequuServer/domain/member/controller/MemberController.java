package org.sopt.lequuServer.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.member.dto.request.MemberNicknameRequestDto;
import org.sopt.lequuServer.domain.member.dto.request.SocialLoginRequestDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberLoginResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberNicknameResponseDto;
import org.sopt.lequuServer.domain.member.service.MemberService;
import org.sopt.lequuServer.global.auth.fegin.kakao.KakaoLoginService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.SET_MEMBER_NICKNAME_SUCCESS;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberLoginResponseDto> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request) {

        return ApiResponse.success(SuccessType.LOGIN_SUCCESS, memberService.login(socialAccessToken, request));
    }

    @GetMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TokenDto> reissue(
            @RequestHeader("Authorization") String refreshToken) {

        return ApiResponse.success(SuccessType.REISSUE_SUCCESS, memberService.reissueToken(refreshToken));
    }

    @PatchMapping("/log-out") // Spring Security 자체의 logout과 겹치지 않기 위해 이렇게 설정
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> logout(Principal principal) {

        memberService.logout(JwtProvider.getUserFromPrincial(principal));
        return ApiResponse.success(SuccessType.LOGOUT_SUCCESS);
    }

    @GetMapping("/kakao")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> kakaoAccessToken(
            @RequestHeader("Authorization") String code) {

        return ApiResponse.success(SuccessType.KAKAO_ACCESS_TOKEN_SUCCESS, kakaoLoginService.getKakaoAccessToken(code));
    }

    @PatchMapping("/nickname")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberNicknameResponseDto> setMemberNickname(Principal principal, @Valid @RequestBody MemberNicknameRequestDto request) {

        return ApiResponse.success(SET_MEMBER_NICKNAME_SUCCESS, memberService.setMemberNickname(JwtProvider.getUserFromPrincial(principal), request));
    }
}