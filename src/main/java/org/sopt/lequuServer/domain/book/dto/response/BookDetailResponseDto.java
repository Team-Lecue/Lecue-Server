package org.sopt.lequuServer.domain.book.dto.response;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.note.dto.response.NoteDetailResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.sticker.dto.response.PostedStickerDetailResponseDto;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.*;

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
        String bookDate = formatLocalDate(book);

        // 레큐노트 리스트를 noteId 기준 내림차순으로 정렬
        List<Note> sortedNotes = book.getNotes().stream()
                .sorted(comparing(Note::getId).reversed())
                .toList();

        // 레큐노트 리스트 가공
        int renderTypeCounter = 1;
        List<NoteDetailResponseDto> noteList = new ArrayList<>();
        for (Note note : sortedNotes) {
            String background = note.getBackground();

            if (background.endsWith(".jpg")) {
                noteList.add(NoteDetailResponseDto.of(note, renderTypeCounter, -1, background));
            } else {
                noteList.add(NoteDetailResponseDto.of(note, renderTypeCounter, Integer.parseInt(background), ""));
            }
            renderTypeCounter = (renderTypeCounter % 6 == 0) ? 1 : renderTypeCounter + 1;
        }

        // 부착된 스티커 리스트 가공
        List<PostedSticker> postedStickers = book.getPostedStickers();
        List<PostedStickerDetailResponseDto> postedStickerList = new ArrayList<>();
        for (PostedSticker postedSticker : postedStickers) {
            postedStickerList.add(PostedStickerDetailResponseDto.of(postedSticker));
        }

        return new BookDetailResponseDto(book.getId(), book.getFavoriteImage(), book.getFavoriteName(),
                book.getTitle(), book.getDescription(), bookDate, book.getMember().getNickname(),
                book.getBackgroundColor(), book.getNotes().size(), noteList, postedStickerList
        );
    }

    private static String formatLocalDate(Book book) {
        LocalDateTime createdAt = book.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return createdAt.format(formatter);
    }
}