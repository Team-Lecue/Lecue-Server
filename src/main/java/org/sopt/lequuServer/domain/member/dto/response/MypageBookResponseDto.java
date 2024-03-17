package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;

import java.util.List;

import static java.util.Comparator.comparing;

public record MypageBookResponseDto(

    List<MypageBooksResponseDto> bookList
) {
    public static MypageBookResponseDto of(List<Book> books) {

        List<MypageBooksResponseDto> bookList = books.stream()
                                                    .sorted(comparing(Book::getCreatedAt).reversed())
                                                    .map(MypageBooksResponseDto::of)
                                                    .toList();

        return new MypageBookResponseDto(bookList);
    }
}
