package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookJpaRepository;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.repository.StickerJpaRepository;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StickerService {

    private final BookJpaRepository bookRepository;
    private final StickerJpaRepository stickerRepository;

    public List<StickerPackResponse> getStickerPackList(Long bookId) {

        if (!bookRepository.existsById(bookId)) {
            throw new CustomException(ErrorType.NOT_FOUND_BOOK_ERROR);
        }

        // 공통 스티커팩
        List<Sticker> defaultStickerPackList = stickerRepository.findStickersByBookId(0L);
        // 해당 레큐북 스티커팩
        List<Sticker> stickerPackList = stickerRepository.findStickersByBookId(bookId);
        defaultStickerPackList.addAll(stickerPackList);

        return StickerPackResponse.of(defaultStickerPackList);
    }
}