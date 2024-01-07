package org.sopt.lequuServer.domain.sticker.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.service.MemberService;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequest;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponse;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.service.StickerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StickerFacade {

    private final StickerService stickerService;
    private final MemberService memberService;
    private final BookService bookService;

    public List<StickerPackResponse> getStickerPackList(Long bookId) {
        return StickerPackResponse.of(stickerService.getStickerPackList(bookId));
    }

    public StickerPostResponse postSticker(Long memberId, StickerPostRequest request) {

        Member member = memberService.getMember(memberId);
        Book book = bookService.getBook(request.bookId());
        Sticker sticker = stickerService.getSticker(request.stickerId());

        PostedSticker postedSticker = PostedSticker.of(request.positionX(), request.positionY(), member, book, sticker);

        return StickerPostResponse.of(stickerService.postSticker(postedSticker));
    }
}
