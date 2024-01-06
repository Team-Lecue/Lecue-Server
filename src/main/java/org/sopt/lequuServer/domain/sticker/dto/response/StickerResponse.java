package org.sopt.lequuServer.domain.sticker.dto.response;

import org.sopt.lequuServer.domain.sticker.model.Sticker;

public record StickerResponse(
        Long stickerId,
        String stickerImage
) {
    public static StickerResponse of(Sticker sticker) {
        return new StickerResponse(
                sticker.getId(),
                sticker.getStickerImage()
        );
    }
}