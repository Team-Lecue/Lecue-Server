package org.sopt.lequuServer.domain.sticker.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequestDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponseDto;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.repository.StickerRepository;
import org.sopt.lequuServer.domain.sticker.service.StickerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StickerFacade {

    private final StickerService stickerService;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final StickerRepository stickerRepository;

    public StickerPostResponseDto postSticker(Long memberId, StickerPostRequestDto request) {

        Member member = memberRepository.findByIdOrThrow(memberId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());
        Sticker sticker = stickerRepository.findByIdOrThrow(request.stickerId());

        PostedSticker postedSticker = PostedSticker.of(request.positionX(), request.positionY(), member, book, sticker);

        return stickerService.postSticker(postedSticker, member, book);
    }
}
