package org.sopt.lequuServer.global.s3.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageFolderName {

    BOOK_FAVORITE_IMAGE_FOLDER_NAME("books/favorite_image/"),
    NOTE_BACKGROUND_IMAGE_FOLDER_NAME("notes/background_image/"),
    STICKER_IMAGE_FOLDER_NAME("stickers/");

    private final String value;

}
