package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record MypageBookListResponseDto(

        Long bookId,
        String bookUuid,
        String title,
        String bookDate,
        int bookBackgroundColor,

        int noteNum
) {
    public static MypageBookListResponseDto of(Book book) {
        LocalDateTime createdAt = book.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String bookDate = createdAt.format(formatter);

        return new MypageBookListResponseDto(
                book.getId(),
                book.getUuid(),
                book.getTitle(),
                bookDate,
                book.getBackgroundColor(),
                book.getNotes().size()
        );
    }
}