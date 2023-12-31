package org.sopt.lequuServer.domain.sticker.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum StickerCategory {

    ALPHABET("알파벳"),
    BIRTHDAY("생일");

    private final String value;
}
