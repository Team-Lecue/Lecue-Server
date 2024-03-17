package org.sopt.lequuServer.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.lequuServer.domain.member.dto.request.MemberNicknameRequestDto;
import org.sopt.lequuServer.domain.member.dto.request.SocialLoginRequestDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberLoginResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberNicknameResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageBookResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageNoteResponseDto;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.util.List;

@Tag(name = "Member", description = "마이페이지 & 로그인 관련 API")
public interface MemberApi {

    @SecurityRequirement(name = "KAKAO Token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "로그인에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MemberLoginResponseDto.class))
    )
    @Operation(summary = "로그인 + 회원가입")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> login(
            @Schema(description = "카카오 Access 토큰 (아래 박스에는 아무거나 입력)", example = "ZC57anP1qo58PNNJhKG1MBz9BImWMWih65gKPXKYAAABjQkwKSIh5oEAb4_jFQ")
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request);


    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Access 토큰 재발급에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = TokenDto.class))
    )
    @Operation(summary = "엑세스 토큰 재발급")
    public ResponseEntity<ApiResponse<TokenDto>> reissue(
            @Schema(description = "Refresh 토큰 (아래 박스에는 아무거나 입력)", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MDUyNTc0MDMsImV4cCI6MTc2ODMyOTQwMywibWVtYmVySWQiOjF9.qKP6AZc9EnA_6DLXJGHzURcJXlER8-mvUnPppEVCGfW4iyQGMS0ZT3f09K0khZBtEXHgMuyKy1m4K-GtSKtRAg")
            @RequestHeader("Authorization") String refreshToken);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "로그아웃에 성공했습니다."
    )
    @Operation(summary = "로그아웃")
    public ResponseEntity<ApiResponse<?>> logout(Principal principal);

    @SecurityRequirement(name = "KAKAO Token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "카카오 엑세스 토큰을 가져오는데 성공했습니다."
    )
    @Operation(summary = "카카오 엑세스 토큰 발급")
    public ResponseEntity<ApiResponse<?>> kakaoAccessToken(
            @Schema(description = "카카오 Authorization 코드 (아래 박스에는 아무거나 입력)", example = "u13t6n_uzDy3VbtRhvmtiOHIVioVMvFPTrrEK_lDgmd7-bY0GMD8FAi8TzUKPXNNAAABjQkwFpiSBpCp5rpDbg")
            @RequestHeader("Authorization") String code);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "유저 닉네임을 설정하여 회원가입에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MemberNicknameResponseDto.class))
    )
    @Operation(summary = "유저 닉네임 설정")
    public ResponseEntity<ApiResponse<MemberNicknameResponseDto>> setMemberNickname(Principal principal, @Valid @RequestBody MemberNicknameRequestDto request);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "마이페이지의 내 레큐북 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MypageBookResponseDto.class))
    )
    @Operation(summary = "마이페이지 레큐북 조회")
    public ResponseEntity<ApiResponse<List<MypageBookResponseDto>>> getMypageBook(Principal principal);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "마이페이지의 내 레큐노트 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = MypageNoteResponseDto.class))
    )
    @Operation(summary = "마이페이지 레큐노트 조회")
    public ResponseEntity<ApiResponse<List<MypageNoteResponseDto>>> getMypageNote(Principal principal);
}