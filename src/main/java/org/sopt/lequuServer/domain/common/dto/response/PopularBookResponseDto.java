package org.sopt.lequuServer.domain.common.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;

public record PopularBookResponseDto(
        Long bookId,
        String bookUuid,
        String favoriteName,
        String favoriteImage
) {
    public static PopularBookResponseDto of(Book book) {
        return new PopularBookResponseDto(book.getId(), book.getUuid(), book.getFavoriteName(),
                book.getFavoriteImage());
    }
}
