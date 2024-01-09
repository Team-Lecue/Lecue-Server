package org.sopt.lequuServer.domain.sticker.dto.response;

import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record PostedStickerDetailResponseDto(
        Long postedStickerId,
        String stickerImage,
        int positionX,
        int positionY
) {
    public static PostedStickerDetailResponseDto of(PostedSticker postedSticker) {
        return new PostedStickerDetailResponseDto(postedSticker.getId(), postedSticker.getSticker().getStickerImage(),
                postedSticker.getPositionX(), postedSticker.getPositionY());
    }
}