package org.sopt.lequuServer.domain.favorite.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.favorite.dto.request.FavoriteCreateRequestDto;
import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.favorite.repository.FavoriteRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteFacade {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void createFavorite(Long memberId, FavoriteCreateRequestDto request) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());

        Favorite favorite = Favorite.of(member, book);
        favoriteRepository.save(favorite);

        book.addFavorite(favorite);
        member.addFavorite(favorite);
    } // memberId와 bookId를 favorite 에 저장하는 로직
}
