package org.sopt.lequuServer.domain.book.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;

public record BookCreateResponseDto(
        Long bookId,
        String bookUuid
) {
    public static BookCreateResponseDto of(Book book) {
        return new BookCreateResponseDto(
                book.getId(),
                book.getUuid()
        );
    }
}
