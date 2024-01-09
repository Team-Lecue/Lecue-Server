package org.sopt.lequuServer.domain.book.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.note.dto.response.NoteDetailResponseDto;
import org.sopt.lequuServer.domain.sticker.dto.response.PostedStickerDetailResponseDto;

import java.util.List;

public record BookDetailResponseDto(
        Long bookId,
        String favoriteImage,
        String favoriteName,
        String title,
        String description,
        String bookDate,
        String bookNickname,
        int bookBackgroundColor,
        int noteNum,
        List<NoteDetailResponseDto> noteList,
        List<PostedStickerDetailResponseDto> postedStickerList
) {
    public static BookDetailResponseDto of(Book book) {
        String bookDate = book.getCreatedAt();

        return new BookDetailResponseDto(book.getId(), book.getFavoriteImage(), book.getFavoriteName(), book.getTitle(), book.getDescription(),
                bookDate, book.getMember().getNickname(), book.getBackgroundColor(), book.getNotes().toArray().length,
                noteList, postedStickerList
        );
    }
}