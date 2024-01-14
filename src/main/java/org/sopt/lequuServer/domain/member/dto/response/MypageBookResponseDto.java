package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.book.model.Book;

import java.util.List;

import static java.util.Comparator.comparing;

public record MypageBookResponseDto(

        @Schema(description = "유저 닉네임", example = "레큐")
        String memberNickname,

        List<MypageBookListResponseDto> bookList
) {
    public static MypageBookResponseDto of(String nickName, List<Book> books) {

        List<MypageBookListResponseDto> bookList = books.stream()
                .sorted(comparing(Book::getCreatedAt).reversed())
                .map(MypageBookListResponseDto::of)
                .toList();

        return new MypageBookResponseDto(nickName, bookList);
    }
}
