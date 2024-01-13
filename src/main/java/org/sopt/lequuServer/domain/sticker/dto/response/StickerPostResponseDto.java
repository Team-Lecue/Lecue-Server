package org.sopt.lequuServer.domain.sticker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record StickerPostResponseDto(
        @Schema(description = "생성된 부착 스티커 고유 id", example = "1")
        Long postedStickerId
) {
    public static StickerPostResponseDto of(PostedSticker postedSticker) {
        return new StickerPostResponseDto(
                postedSticker.getId()
        );
    }
}