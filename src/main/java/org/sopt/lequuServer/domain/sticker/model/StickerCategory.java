package org.sopt.lequuServer.domain.sticker.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum StickerCategory {

    CUSTOM("커스텀"),
    EVENT("이벤트"),
    CAT("고양이"),
    ITEM("아이템");

    private final String value;
}
