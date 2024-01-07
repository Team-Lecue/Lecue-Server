package org.sopt.lequuServer.domain.sticker.dto.response;

import org.sopt.lequuServer.domain.sticker.model.Sticker;

public record StickerResponseDto(
        Long stickerId,
        String stickerImage
) {
    public static StickerResponseDto of(Sticker sticker) {
        return new StickerResponseDto(
                sticker.getId(),
                sticker.getStickerImage()
        );
    }
}