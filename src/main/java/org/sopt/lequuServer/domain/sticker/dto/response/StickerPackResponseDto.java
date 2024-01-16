package org.sopt.lequuServer.domain.sticker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.model.StickerCategory;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public record StickerPackResponseDto(

        @Schema(description = "스티커 카테코리", example = "생일")
        String stickerCategory,

        List<StickerResponseDto> stickerDtos
) {

    // 스티커의 카테고리를 기준으로 분류해 List 생성
    public static List<StickerPackResponseDto> of(List<Sticker> stickers) {
        Map<StickerCategory, List<StickerResponseDto>> groupedStickerList = stickers.stream()
                .collect(groupingBy(
                        Sticker::getCategory,
                        mapping(
                                StickerResponseDto::of,
                                toList()
                        )
                ));

        return groupedStickerList.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new StickerPackResponseDto(entry.getKey().getValue(), entry.getValue()))
                .collect(toList());
    }
}