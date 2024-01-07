package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequest;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponse;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.repository.PostedStickerRepository;
import org.sopt.lequuServer.domain.sticker.repository.StickerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StickerService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final StickerRepository stickerRepository;
    private final PostedStickerRepository postedStickerRepository;

    public List<StickerPackResponse> getStickerPackList(Long bookId) {

        // 공통 스티커팩 + 해당 레큐북 스티커팩
        List<Long> bookIds = Arrays.asList(0L, bookId);
        List<Sticker> stickerPack = stickerRepository.findStickersByBookIds(bookIds);

        return StickerPackResponse.of(stickerPack);
    }

    @Transactional
    public StickerPostResponse postSticker(Long memberId, StickerPostRequest request) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());
        Sticker sticker = stickerRepository.findByIdOrThrow(request.stickerId());

        return StickerPostResponse.of(postedStickerRepository.save(PostedSticker.of(request.positionX(), request.positionY(), member, book, sticker)));
    }
}