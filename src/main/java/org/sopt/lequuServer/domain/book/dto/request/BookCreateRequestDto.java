package org.sopt.lequuServer.domain.book.dto.request;

import jakarta.validation.constraints.*;

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

        @Min(value = 0, message = "레큐북 배경색은 0 또는 1이어야합니다.")
        @Max(value = 1, message = "레큐북 배경색은 0 또는 1이어야합니다.")
        int backgroundColor
) {
}
