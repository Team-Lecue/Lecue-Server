package org.sopt.lequuServer.domain.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookCreateRequest(

        @NotBlank
        @Size(min = 1, max = 12, message = "최애 이름은 1자 이상 12자 이하여야합니다.")
        String favoriteName,

        String favoriteImage,

        @NotBlank
        @Size(min = 1, max = 12, message = "레큐북 제목은 1자 이상 12자 이하여야합니다.")
        String title,

        @NotBlank
        @Size(min = 1, max = 100, message = "레큐북 소개는 1자 이상 100자 이하여야합니다.")
        String description,

        int backgroundColor
) {
}
