package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;

import java.util.List;

public record MypageBookResponseDto(
        String memberNickname,
        List<MypageBookListResponseDto> mypageBookList
) {
    public static MypageBookResponseDto of(String nickName, List<Book> books) {
        List<MypageBookListResponseDto> mypageBookList = books.stream()
                .map(MypageBookListResponseDto::of)
                .toList();

        return new MypageBookResponseDto(nickName, mypageBookList);
    }
}
