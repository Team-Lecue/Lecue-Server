package org.sopt.lequuServer.domain.sticker.dto.response;

import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record StickerPostResponse(
        Long postedStickerId
) {
    public static StickerPostResponse of(PostedSticker postedSticker) {
        return new StickerPostResponse(
                postedSticker.getId()
        );
    }
}