package org.sopt.lequuServer.domain.favorite.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.favorite.dto.request.FavoriteRequestDto;
import org.sopt.lequuServer.domain.favorite.dto.response.FavoriteBookResponseDto;
import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.favorite.repository.FavoriteRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteFacade {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void createFavorite(Long memberId, FavoriteRequestDto request) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());

        if (member.getFavorites().stream()
            .anyMatch(favorite -> favorite.getBook().getId().equals(request.bookId()))) {
            return; // 이미 해당 책이 즐겨찾기에 추가된 경우, 추가 작업을 하지 않고 함수를 종료
        }

        Favorite favorite = Favorite.of(member, book);
        favoriteRepository.save(favorite);
    } // memberId와 bookId를 favorite 에 저장하는 로직

    public List<FavoriteBookResponseDto> getFavorite(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        List<Favorite> favorites = favoriteRepository.findByMemberOrderByCreatedAtDesc(member);

        return favorites.stream()
            .limit(3) // 최신순 3개만 가져오기
            .map(favorite -> FavoriteBookResponseDto.of(favorite.getBook()))
            .collect(Collectors.toList());
    } // memberId를 이용해 그 멤버가 즐겨찾기 해놓은 레큐북 목록들을 반환하는 로직

    @Transactional
    public void deleteFavorite(Long memberId, FavoriteRequestDto request) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());

        Favorite favorite = favoriteRepository.findByMemberAndBook(member, book)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FAVORITE_ERROR));
        favoriteRepository.delete(favorite);
    }
}