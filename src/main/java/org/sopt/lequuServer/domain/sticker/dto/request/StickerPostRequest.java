package org.sopt.lequuServer.domain.sticker.dto.request;

public record StickerPostRequest(
        Long bookId,
        Long stickerId,
        int positionX,
        int positionY
) {
}