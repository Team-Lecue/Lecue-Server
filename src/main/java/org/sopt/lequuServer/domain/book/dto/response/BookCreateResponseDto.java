package org.sopt.lequuServer.domain.book.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.book.model.Book;

public record BookCreateResponseDto(

        @Schema(description = "레큐북 고유 id", example = "1")
        Long bookId,

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid
) {
    public static BookCreateResponseDto of(Book book) {
        return new BookCreateResponseDto(
                book.getId(),
                book.getUuid()
        );
    }
}
