package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.book.model.Book;

import java.time.format.DateTimeFormatter;

public record MypageBooksResponseDto(

        @Schema(description = "레큐북 고유 id", example = "1")
        Long bookId,

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,

        @Schema(description = "최애 이름", example = "LeoJ")
        String favoriteName,

        @Schema(description = "레큐북 제목", example = "1번째 레큐북")
        String title,

        @Schema(description = "레큐북 생성 일시", example = "2024.01.11")
        String bookDate,

        @Schema(description = "레큐노트 개수", example = "1974")
        int noteNum
) {
    public static MypageBooksResponseDto of(Book book) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String bookDate = book.getCreatedAt().format(formatter);

        return new MypageBooksResponseDto(
                book.getId(),
                book.getUuid(),
                book.getFavoriteName(),
                book.getTitle(),
                bookDate,
                book.getNotes().size()
        );
    }
}