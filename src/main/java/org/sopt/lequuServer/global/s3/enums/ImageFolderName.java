package org.sopt.lequuServer.global.s3.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImageFolderName {

    ROLLING_PAPER_FAVORITE_IMAGE_FOLDER_NAME("rolling_papers/favorite_image/"),
    POSTIT_BACKGROUND_IMAGE_FOLDER_NAME("postits/background_image/");

    private final String value;

}
