package org.sopt.lequuServer.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.member.dto.request.MemberNicknameRequestDto;
import org.sopt.lequuServer.domain.member.dto.request.SocialLoginRequestDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberLoginResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberNicknameResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageBookResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageNoteResponseDto;
import org.sopt.lequuServer.domain.member.service.MemberService;
import org.sopt.lequuServer.global.auth.fegin.kakao.KakaoLoginService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Member", description = "마이페이지 & 로그인 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    @SecurityRequirement(name = "KAKAO Token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "로그인에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MemberLoginResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인 + 회원가입")
    public ApiResponse<MemberLoginResponseDto> login(
            @Schema(description = "카카오 Access 토큰 (아래 박스에는 아무거나 입력)", example = "ZC57anP1qo58PNNJhKG1MBz9BImWMWih65gKPXKYAAABjQkwKSIh5oEAb4_jFQ")
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request) {

        return ApiResponse.success(SuccessType.LOGIN_SUCCESS, memberService.login(socialAccessToken, request));
    }

    @GetMapping("/reissue")
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Access 토큰 재발급에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = TokenDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "엑세스 토큰 재발급")
    public ApiResponse<TokenDto> reissue(
            @Schema(description = "Refresh 토큰 (아래 박스에는 아무거나 입력)", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDUyNTc0MDMsImV4cCI6MTc2ODMyOTQwMywibWVtYmVySWQiOjF9.qKP6AZc9EnA_6DLXJGHzURcJXlER8-mvUnPppEVCGfW4iyQGMS0ZT3f09K0khZBtEXHgMuyKy1m4K-GtSKtRAg")
            @RequestHeader("Authorization") String refreshToken) {

        return ApiResponse.success(SuccessType.REISSUE_SUCCESS, memberService.reissueToken(refreshToken));
    }

    @PatchMapping("/log-out") // Spring Security 자체의 logout과 겹치지 않기 위해 이렇게 설정
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "로그아웃에 성공했습니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그아웃")
    public ApiResponse<?> logout(Principal principal) {

        memberService.logout(JwtProvider.getUserFromPrincial(principal));
        return ApiResponse.success(SuccessType.LOGOUT_SUCCESS);
    }

    @GetMapping("/kakao")
    @SecurityRequirement(name = "KAKAO Token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "카카오 엑세스 토큰을 가져오는데 성공했습니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "카카오 엑세스 토큰 발급")
    public ApiResponse<?> kakaoAccessToken(
            @Schema(description = "카카오 Authorization 코드 (아래 박스에는 아무거나 입력)", example = "u13t6n_uzDy3VbtRhvmtiOHIVioVMvFPTrrEK_lDgmd7-bY0GMD8FAi8TzUKPXNNAAABjQkwFpiSBpCp5rpDbg")
            @RequestHeader("Authorization") String code) {
        return ApiResponse.success(SuccessType.KAKAO_ACCESS_TOKEN_SUCCESS, kakaoLoginService.getKakaoAccessToken(code));
    }

    @PatchMapping("/nickname")
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "유저 닉네임을 설정하여 회원가입에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MemberNicknameResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저 닉네임 설정")
    public ApiResponse<MemberNicknameResponseDto> setMemberNickname(Principal principal, @Valid @RequestBody MemberNicknameRequestDto request) {

        return ApiResponse.success(SET_MEMBER_NICKNAME_SUCCESS, memberService.setMemberNickname(JwtProvider.getUserFromPrincial(principal), request));
    }

    @GetMapping("/mypage/book")
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "마이페이지의 유저 닉네임과 내 레큐북 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MypageBookResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "마이페이지 레큐북 조회")
    public ApiResponse<MypageBookResponseDto> getMypageBook(Principal principal) {
        return ApiResponse.success(GET_MYPAGE_BOOK_SUCCESS, memberService.getMypageBook(JwtProvider.getUserFromPrincial(principal)));
    }

    @GetMapping("/mypage/note")
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "마이페이지의 유저 닉네임과 내 레큐노트 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MypageNoteResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "마이페이지 레큐노트 조회")
    public ApiResponse<MypageNoteResponseDto> getMypageNote(Principal principal) {
        return ApiResponse.success(GET_MYPAGE_NOTE_SUCCESS, memberService.getMypageNote(JwtProvider.getUserFromPrincial(principal)));
    }
}