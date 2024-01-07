package org.sopt.lequuServer.domain.sticker.dto.request;

public record StickerPostRequestDto(
        Long bookId,
        Long stickerId,
        int positionX,
        int positionY
) {
}