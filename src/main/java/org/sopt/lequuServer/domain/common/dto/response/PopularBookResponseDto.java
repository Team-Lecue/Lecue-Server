package org.sopt.lequuServer.domain.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.book.model.Book;

public record PopularBookResponseDto(
        @Schema(description = "레큐북 고유 id", example = "1")
        Long bookId,
        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,
        @Schema(description = "최애 이름", example = "LeoJ")
        String favoriteName,
        @Schema(description = "최애 사진", example = "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/books/favorite_image/b4006561-382b-479e-ae1d-e841922e883f.jpg")
        String favoriteImage
) {
    public static PopularBookResponseDto of(Book book) {
        return new PopularBookResponseDto(book.getId(), book.getUuid(), book.getFavoriteName(),
                book.getFavoriteImage());
    }
}
