package org.sopt.lequuServer.domain.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookCreateRequestDto(

        @NotBlank
        @Size(min = 1, max = 15, message = "최애 이름은 1자 이상 15자 이하여야합니다.")
        String favoriteName,

        String favoriteImage,

        @NotBlank
        @Size(min = 1, max = 25, message = "레큐북 제목은 1자 이상 25자 이하여야합니다.")
        String title,

        @NotBlank
        @Size(min = 1, max = 65, message = "레큐북 소개는 1자 이상 65자 이하여야합니다.")
        String description,

        // TODO 숫자 1 or 2로 제한할 수 있는 것 추가
        int backgroundColor
) {
}
