package org.sopt.lequuServer.domain.favorite.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteRequestDto(
        @Schema(example = "1")
        Long bookId
) {
}