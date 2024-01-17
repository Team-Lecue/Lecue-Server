package org.sopt.lequuServer.domain.book.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record BookCreateRequestDto(

        @Schema(example = "LeoJ")
        @NotBlank
        @Size(min = 1, max = 15, message = "최애 이름은 1자 이상 15자 이하여야합니다.")
        String favoriteName,

        @Schema(example = "b4006561-382b-479e-ae1d-e841922e883f.jpg")
        String favoriteImage,

        @Schema(example = "1번째 레큐북")
        @NotBlank
        @Size(min = 1, max = 25, message = "레큐북 제목은 1자 이상 25자 이하여야합니다.")
        String title,

        @Schema(example = "레큐북 내용입니다.")
        @NotBlank
        @Size(min = 1, max = 65, message = "레큐북 소개는 1자 이상 65자 이하여야합니다.")
        String description,

        @Schema(example = "#FFFFFF")
        @Pattern(regexp = "^(#[A-Za-z0-9]{3,6})$", message = "색상은 #으로 시작하는 3~6 사이의 색깔코드여야 합니다.")
        String backgroundColor
) {
}
