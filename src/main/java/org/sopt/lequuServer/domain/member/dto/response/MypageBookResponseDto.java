package org.sopt.lequuServer.domain.member.dto.response;

import static java.util.Comparator.comparing;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.sopt.lequuServer.domain.book.model.Book;

public record MypageBookResponseDto(

        @Schema(description = "레큐북 고유 id", example = "1")
        Long bookId,

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,

        @Schema(description = "레큐북 즐겨찾기 등록 여부", example = "true")
        Boolean isFavorite,

        @Schema(description = "최애 이름", example = "LeoJ")
        String favoriteName,

        @Schema(description = "레큐북 제목", example = "1번째 레큐북")
        String title,

        @Schema(description = "레큐북 생성 일시", example = "2024.01.11")
        String bookDate,

        @Schema(description = "레큐노트 개수", example = "1974")
        int noteNum
) {
    public static List<MypageBookResponseDto> of(List<Book> books, List<Book> favoriteBooks) {

        return books.stream()
                .sorted(comparing(Book::getCreatedAt).reversed())
                .map(book ->
                    new MypageBookResponseDto(
                            book.getId(),
                            book.getUuid(),
                            favoriteBooks.contains(book),
                            book.getFavoriteName(),
                            book.getTitle(),
                            book.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                            book.getNotes().size()
                    )
                )
                .toList();
    }
}