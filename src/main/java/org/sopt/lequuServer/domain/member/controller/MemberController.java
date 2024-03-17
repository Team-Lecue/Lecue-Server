package org.sopt.lequuServer.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.favorite.dto.response.FavoriteBookResponseDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponseDto>> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @RequestBody SocialLoginRequestDto request) {

        return ResponseEntity.ok(ApiResponse.success(SuccessType.LOGIN_SUCCESS, memberService.login(socialAccessToken, request)));
    }

    @GetMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenDto>> reissue(
            @RequestHeader("Authorization") String refreshToken) {

        return ResponseEntity.ok(ApiResponse.success(SuccessType.REISSUE_SUCCESS, memberService.reissueToken(refreshToken)));
    }

    @PatchMapping("/log-out") // Spring Security 자체의 logout과 겹치지 않기 위해 이렇게 설정
    public ResponseEntity<ApiResponse<?>> logout(Principal principal) {

        memberService.logout(JwtProvider.getUserFromPrincial(principal));
        return ResponseEntity.ok(ApiResponse.success(SuccessType.LOGOUT_SUCCESS));
    }

    @GetMapping("/kakao")
    public ResponseEntity<ApiResponse<?>> kakaoAccessToken(
            @RequestHeader("Authorization") String code) {
        return ResponseEntity.ok(ApiResponse.success(SuccessType.KAKAO_ACCESS_TOKEN_SUCCESS, kakaoLoginService.getKakaoAccessToken(code)));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<ApiResponse<MemberNicknameResponseDto>> setMemberNickname(Principal principal, @Valid @RequestBody MemberNicknameRequestDto request) {

        return ResponseEntity.ok(ApiResponse.success(SET_MEMBER_NICKNAME_SUCCESS, memberService.setMemberNickname(JwtProvider.getUserFromPrincial(principal), request)));
    }

    @GetMapping("/mypage/book")
    public ResponseEntity<ApiResponse<List<MypageBookResponseDto>>> getMypageBook(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(GET_MYPAGE_BOOK_SUCCESS, memberService.getMypageBook(JwtProvider.getUserFromPrincial(principal))));
    }

    @GetMapping("/mypage/note")
    public ResponseEntity<ApiResponse<List<MypageNoteResponseDto>>> getMypageNote(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(GET_MYPAGE_NOTE_SUCCESS, memberService.getMypageNote(JwtProvider.getUserFromPrincial(principal))));
    }

    @GetMapping("/mypage/favorite")
    public ResponseEntity<ApiResponse<List<FavoriteBookResponseDto>>> getMypageFavorite(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(GET_MYPAGE_FAVORITE_SUCCESS, memberService.getMypageFavorite(JwtProvider.getUserFromPrincial(principal))));
    }
}