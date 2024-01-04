package org.sopt.lequuServer.domain.book.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;

public record BookCreateResponse(
        Long bookId,
        String bookUuid
) {
    public static BookCreateResponse of(Book book) {
        return new BookCreateResponse(
                book.getId(),
                book.getUuid()
        );
    }
}
