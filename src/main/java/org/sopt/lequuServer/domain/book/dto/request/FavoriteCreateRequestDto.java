package org.sopt.lequuServer.domain.book.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteCreateRequestDto(
    @Schema(example = "1")
    Long bookId
) {
}