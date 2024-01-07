package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookJpaRepository;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponse;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.repository.PostedStickerRepository;
import org.sopt.lequuServer.domain.sticker.repository.StickerRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.NOT_FOUND_BOOK_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StickerService {

    private final MemberRepository memberRepository;
    private final BookRepsitory bookRepository;
    private final StickerRepository stickerRepository;
    private final PostedStickerRepository postedStickerRepository;

    public List<StickerPackResponse> getStickerPackList(Long bookId) {

        // 공통 스티커팩 + 해당 레큐북 스티커팩
        List<Long> bookIds = Arrays.asList(0L, bookId);
        List<Sticker> stickerPack = stickerRepository.findStickersByBookIds(bookIds);

        return StickerPackResponse.of(stickerPack);
    }

    @Transactional
    public StickerPostResponse postSticker(Long userId, StickerPostRequest request) {
        User user = userRepository.findByIdOrThrow(userId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());
        Sticker sticker = stickerRepository.findByIdOrThrow(request.stickerId());

        return StickerPostResponse.of(postedStickerRepository.save(PostedSticker.of(request.positionX(), request.positionY(), user, book, sticker)));
    }
}