package org.sopt.lequuServer.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
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
import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Member", description = "마이페이지 & 로그인 관련 API")
@SecurityRequirement(name = "JWT Auth")
public class MemberController {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "로그인에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = PopularBookResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인 + 회원가입")
    public ApiResponse<MemberLoginResponseDto> login(
            @Schema(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDQ2MTk0NDgsImV4cCI6MTczNjE1NTQ0OCwibWVtYmVySWQiOjF9.8l5CdAvQ8sWQghu9xlbHxrtlGc6W8UR09CkiCH0DRUWgizH5c-5Zfdp_MKMz0fk_0y4V4VfXliL6wikulwaGSg")
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request) {

        return ApiResponse.success(SuccessType.LOGIN_SUCCESS, memberService.login(socialAccessToken, request));
    }

    @GetMapping("/reissue")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Access 토큰 재발급에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = TokenDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "엑세스 토큰 재발행")
    public ApiResponse<TokenDto> reissue(
            @Schema(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2ODkyNzA1NDgsImV4cCI6MTY4OTI4MTM0OH0.e1rHHRLwWRSPZ6LhWBRwqzvcSJN3f-KTyKFqNt0uAvJhnlsaUB_qS0ApMhGP_JdH3e9vNNRq1RXgayLMw76cIA")
            @RequestHeader("Authorization") String refreshToken) {

        return ApiResponse.success(SuccessType.REISSUE_SUCCESS, memberService.reissueToken(refreshToken));
    }

    @PatchMapping("/log-out") // Spring Security 자체의 logout과 겹치지 않기 위해 이렇게 설정
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
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "카카오 엑세스 토큰을 가져오는데 성공했습니다."
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "카카오 엑세스 토큰")
    public ApiResponse<?> kakaoAccessToken(
            @Schema(description = "카카오 코드", example = "IQ4R8vj7groeB_MdsFsCoGnFy3ZmRApxMhDlB08Hm-YY2MyZGZqZmiXMHroYNnpnTyKfMwo9dBEAAAGJW3CLKQ")
            @RequestHeader("Authorization") String code) {
        return ApiResponse.success(SuccessType.KAKAO_ACCESS_TOKEN_SUCCESS, kakaoLoginService.getKakaoAccessToken(code));
    }

    @PatchMapping("/nickname")
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
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "마이페이지 내 레큐노트 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MypageNoteResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "마이페이지 레큐노트 조회")
    public ApiResponse<List<MypageNoteResponseDto>> getMypageNote(Principal principal) {
        return ApiResponse.success(GET_MYPAGE_NOTE_SUCCESS, memberService.getMypageNote(JwtProvider.getUserFromPrincial(principal)));
    }
}