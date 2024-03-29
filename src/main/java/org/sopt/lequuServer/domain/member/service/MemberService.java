package org.sopt.lequuServer.domain.member.service;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.INVALID_SOCIAL_ACCESS_TOKEN;
import static org.sopt.lequuServer.global.exception.enums.ErrorType.INVALID_TOKEN_HEADER_ERROR;
import static org.sopt.lequuServer.global.exception.enums.ErrorType.NOT_FOUND_MEMBER_ERROR;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.favorite.dto.response.FavoriteBookResponseDto;
import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.favorite.repository.FavoriteRepository;
import org.sopt.lequuServer.domain.member.dto.request.MemberNicknameRequestDto;
import org.sopt.lequuServer.domain.member.dto.request.SocialLoginRequestDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberLoginResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MemberNicknameResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageBookResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageNoteResponseDto;
import org.sopt.lequuServer.domain.member.dto.response.MypageResponseDto;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.global.BadWordFilterService;
import org.sopt.lequuServer.global.auth.fegin.kakao.KakaoLoginService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.auth.jwt.TokenDto;
import org.sopt.lequuServer.global.auth.security.UserAuthentication;
import org.sopt.lequuServer.global.common.logging.LoggingMessage;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final FavoriteRepository favoriteRepository;

    private final JwtProvider jwtProvider;
    private final KakaoLoginService kakaoLoginService;
    private final BadWordFilterService badWordFilterService;

    private static String parseTokenString(String tokenString) {
        String[] strings = tokenString.split(" ");
        if (strings.length != 2) {
            throw new CustomException(INVALID_TOKEN_HEADER_ERROR);
        }
        return strings[1];
    }

    @Transactional
    public MemberLoginResponseDto login(String socialAccessToken, SocialLoginRequestDto request) {

        socialAccessToken = parseTokenString(socialAccessToken);

        SocialPlatform socialPlatform = request.getSocialPlatform();
        String socialId = login(socialPlatform, socialAccessToken);

        boolean isRegistered = isUserBySocialAndSocialId(socialPlatform, socialId);
        if (!isRegistered) {
            Member member = Member.builder()
                .socialPlatform(socialPlatform)
                .socialId(socialId).build();

            memberRepository.save(member);
        }

        Member loginMember = getUserBySocialAndSocialId(socialPlatform, socialId);
        // 카카오 로그인은 정보 더 많이 받아올 수 있으므로 추가 설정
        if (socialPlatform == SocialPlatform.KAKAO) {
            kakaoLoginService.setKakaoInfo(loginMember, socialAccessToken);
        }

        if (!isRegistered && socialPlatform == SocialPlatform.KAKAO) {
            log.info(LoggingMessage.memberRegisterLogMessage(loginMember));
        }

        TokenDto tokenDto = jwtProvider.issueToken(
            new UserAuthentication(loginMember.getId(), null, null));

        return MemberLoginResponseDto.of(loginMember, tokenDto);
    }

    @Transactional
    public TokenDto reissueToken(String refreshToken) {

        refreshToken = parseTokenString(refreshToken);

        Long memberId = jwtProvider.validateRefreshToken(refreshToken);
        validateMemberId(memberId);  // memberId가 DB에 저장된 유효한 값인지 검사

        jwtProvider.deleteRefreshToken(memberId);
        return jwtProvider.issueToken(new UserAuthentication(memberId, null, null));
    }

    @Transactional
    public void logout(Long memberId) {
        jwtProvider.deleteRefreshToken(memberId);
    }

    private void validateMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new CustomException(NOT_FOUND_MEMBER_ERROR);
        }
    }

    private Member getUserBySocialAndSocialId(SocialPlatform socialPlatform, String socialId) {
        return memberRepository.findBySocialPlatformAndSocialId(socialPlatform, socialId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER_ERROR));
    }

    private boolean isUserBySocialAndSocialId(SocialPlatform socialPlatform, String socialId) {
        return memberRepository.existsBySocialPlatformAndSocialId(socialPlatform, socialId);
    }

    private String login(SocialPlatform socialPlatform, String socialAccessToken) {
        return switch (socialPlatform.toString()) {
            case "KAKAO" -> kakaoLoginService.getKakaoId(socialAccessToken);
            default -> throw new CustomException(INVALID_SOCIAL_ACCESS_TOKEN);
        };
    }

    @Transactional
    public MemberNicknameResponseDto setMemberNickname(Long memberId,
        MemberNicknameRequestDto request) {
        if (memberRepository.existsByNickname(request.nickname())) {
            throw new CustomException(ErrorType.NICKNAME_DUP_ERROR);
        }

        Member member = memberRepository.findByIdOrThrow(memberId);
        member.updateNickname(
            badWordFilterService.badWordChange(memberId, request.nickname().strip()));
        return MemberNicknameResponseDto.of(memberId);
    }

    public List<MypageBookResponseDto> getMypageBook(Long memberId) {

        List<Book> byBook = bookRepository.findByMemberId(memberId);
        List<Book> favoriteBooks = favoriteRepository.findByMemberId(memberId).stream()
            .map(Favorite::getBook).toList();

        return MypageBookResponseDto.of(byBook, favoriteBooks);
    }

    public List<MypageNoteResponseDto> getMypageNote(Long memberId) {

        // 회원 id 찾기
        Member member = memberRepository.findByIdOrThrow(memberId);

        // 회원이 소유한 Note 리스트 가져오기
        List<Note> notes = member.getNotes();

        return MypageNoteResponseDto.of(notes);
    }

    public List<FavoriteBookResponseDto> getMypageFavorite(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        List<Favorite> favorites = favoriteRepository.findByMember(member);

        return favorites.stream()
            .map(favorite -> FavoriteBookResponseDto.of(favorite.getBook()))
            .collect(Collectors.toList());
    }

    public MypageResponseDto getMypage(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);

        String nickname = member.getNickname();

        return new MypageResponseDto(nickname);
    }
}



