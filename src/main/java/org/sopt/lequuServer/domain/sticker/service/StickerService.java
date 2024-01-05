package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookJpaRepository;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequest;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponse;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.repository.PostedStickerJpaRepository;
import org.sopt.lequuServer.domain.sticker.repository.StickerJpaRepository;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.domain.user.repository.UserJpaRepository;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.NOT_FOUND_BOOK_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StickerService {

    private final UserJpaRepository userRepository;
    private final BookJpaRepository bookRepository;
    private final StickerJpaRepository stickerRepository;
    private final PostedStickerJpaRepository postedStickerRepository;

    public List<StickerPackResponse> getStickerPackList(Long bookId) {

        if (!bookRepository.existsById(bookId)) {
            throw new CustomException(NOT_FOUND_BOOK_ERROR);
        }

        // 공통 스티커팩
        List<Sticker> defaultStickerPackList = stickerRepository.findStickersByBookId(0L);
        // 해당 레큐북 스티커팩
        List<Sticker> stickerPackList = stickerRepository.findStickersByBookId(bookId);
        defaultStickerPackList.addAll(stickerPackList);

        return StickerPackResponse.of(defaultStickerPackList);
    }

    @Transactional
    public StickerPostResponse postSticker(Long userId, StickerPostRequest request) {
        User user = userRepository.findByIdOrThrow(userId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());
        Sticker sticker = stickerRepository.findByIdOrThrow(request.stickerId());

        return StickerPostResponse.of(postedStickerRepository.save(PostedSticker.of(request.positionX(), request.positionY(), user, book, sticker)));
    }
}