package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponseDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponseDto;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
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

    private final StickerRepository stickerRepository;
    private final PostedStickerRepository postedStickerRepository;

    public List<StickerPackResponseDto> getStickerPackList(Long bookId) {
        // 공통 스티커팩 + 해당 레큐북 스티커팩
        List<Long> bookIds = Arrays.asList(0L, bookId);
        return StickerPackResponseDto.of(stickerRepository.findStickersByBookIds(bookIds));
    }

    @Transactional
    public StickerPostResponseDto postSticker(PostedSticker postedSticker, Member member, Book book) {

        member.addPostedSticker(postedSticker);
        book.addPostedSticker(postedSticker);

        return StickerPostResponseDto.of(book.getUuid(), postedStickerRepository.save(postedSticker));
    }
}