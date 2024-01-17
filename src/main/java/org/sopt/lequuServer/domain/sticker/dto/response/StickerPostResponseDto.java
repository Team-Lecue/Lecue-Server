package org.sopt.lequuServer.domain.sticker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record StickerPostResponseDto(

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,

        @Schema(description = "생성된 부착 스티커 고유 id", example = "1")
        Long postedStickerId
) {
    public static StickerPostResponseDto of(String bookUuid, PostedSticker postedSticker) {
        return new StickerPostResponseDto(
                bookUuid,
                postedSticker.getId()
        );
    }
}