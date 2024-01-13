package org.sopt.lequuServer.domain.sticker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record StickerPostRequestDto(

        @Schema(example = "1")
        Long bookId,

        @Schema(example = "1")
        Long stickerId,

        @Schema(example = "30")
        int positionX,
        
        @Schema(example = "60")
        int positionY
) {
}