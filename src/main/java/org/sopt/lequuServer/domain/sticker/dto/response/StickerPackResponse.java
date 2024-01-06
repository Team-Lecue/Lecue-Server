package org.sopt.lequuServer.domain.sticker.dto.response;

import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.domain.sticker.model.StickerCategory;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public record StickerPackResponse(
        String stickerCategory,
        List<StickerResponse> stickerList
) {

    // 스티커의 카테고리를 기준으로 분류해 List 생성
    public static List<StickerPackResponse> of(List<Sticker> stickers) {
        Map<StickerCategory, List<StickerResponse>> groupedStickerList = stickers.stream()
                .collect(groupingBy(
                        Sticker::getCategory,
                        mapping(
                                StickerResponse::of,
                                toList()
                        )
                ));

        return groupedStickerList.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new StickerPackResponse(entry.getKey().getValue(), entry.getValue()))
                .collect(toList());
    }
}