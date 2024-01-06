package org.sopt.lequuServer.domain.sticker.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
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

    public List<StickerPackResponse> getStickerPackList(Long bookId) {

        // 공통 스티커팩 + 해당 레큐북 스티커팩
        List<Long> bookIds = Arrays.asList(0L, bookId);
        List<Sticker> stickerPack = stickerRepository.findStickersByBookIds(bookIds);

        return StickerPackResponse.of(stickerPack);
    }
}