package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
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

    private final StickerRepository stickerRepository;
    private final PostedStickerRepository postedStickerRepository;

    public List<Sticker> getStickerPackList(Long bookId) {
        // 공통 스티커팩 + 해당 레큐북 스티커팩
        List<Long> bookIds = Arrays.asList(0L, bookId);
        return stickerRepository.findStickersByBookIds(bookIds);
    }

    public Sticker getSticker(Long stickerId) {
        return stickerRepository.findByIdOrThrow(stickerId);
    }

    @Transactional
    public PostedSticker postSticker(PostedSticker postedSticker) {
        return postedStickerRepository.save(postedSticker);
    }
}