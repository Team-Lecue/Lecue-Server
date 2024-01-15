package org.sopt.lequuServer.domain.book.dto.response;

import static java.util.Comparator.comparing;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.note.dto.response.NoteDetailResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.sticker.dto.response.PostedStickerDetailResponseDto;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;

public record BookDetailResponseDto(

        @Schema(description = "레큐북 고유 id", example = "1")
        Long bookId,

        @Schema(description = "최애 사진", example = "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/books/favorite_image/b4006561-382b-479e-ae1d-e841922e883f.jpg")
        String favoriteImage,

        @Schema(description = "최애 이름", example = "LeoJ")
        String favoriteName,

        @Schema(description = "레큐북 제목", example = "1번째 레큐북")
        String title,

        @Schema(description = "레큐북 소개", example = "레큐북의 내용입니다.")
        String description,

        @Schema(description = "레큐북 생성 일시", example = "2024.01.11")
        String bookDate,

        @Schema(description = "레큐북 작성자 닉네임", example = "예딘")
        String bookNickname,

        @Schema(description = "레큐북 배경색", example = "#FFFFFF")
        String bookBackgroundColor,

        @Schema(description = "레큐 노트 개수", example = "100")
        int noteNum,

        List<NoteDetailResponseDto> noteList,

        List<PostedStickerDetailResponseDto> postedStickerList
) {
    public static BookDetailResponseDto of(Book book) {
        String bookDate = formatLocalDate(book);

        // 레큐노트 리스트를 noteId 기준 내림차순으로 정렬
        List<Note> sortedNotes = book.getNotes().stream()
                .sorted(comparing(Note::getCreatedAt).reversed())
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