package org.sopt.lequuServer.domain.sticker.dto.response;

import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record StickerPostResponseDto(
        Long postedStickerId
) {
    public static StickerPostResponseDto of(PostedSticker postedSticker) {
        return new StickerPostResponseDto(
                postedSticker.getId()
        );
    }
}