package org.sopt.lequuServer.domain.sticker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record StickerPacksResponseDto(

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,

        List<StickerPackResponseDto> stickerPackList
) {
    public static StickerPacksResponseDto of(String bookUuid, List<StickerPackResponseDto> stickerPackList) {
        return new StickerPacksResponseDto(
                bookUuid,
                stickerPackList
        );
    }
}
