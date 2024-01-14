package org.sopt.lequuServer.domain.sticker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record PostedStickerDetailResponseDto(

        @Schema(description = "스티커 고유 id", example = "1")
        Long postedStickerId,

        @Schema(description = "스티커 이미지", example = "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/birth_1.svg")
        String stickerImage,

        @Schema(description = "스티커 위치(X축)", example = "30")
        int positionX,

        @Schema(description = "스티커 위치(Y축)", example = "60")
        int positionY
) {
    public static PostedStickerDetailResponseDto of(PostedSticker postedSticker) {
        return new PostedStickerDetailResponseDto(postedSticker.getId(), postedSticker.getSticker().getStickerImage(),
                postedSticker.getPositionX(), postedSticker.getPositionY());
    }
}