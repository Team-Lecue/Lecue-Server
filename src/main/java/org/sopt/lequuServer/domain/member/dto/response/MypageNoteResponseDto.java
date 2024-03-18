package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.note.model.Note;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Comparator.comparing;

public record MypageNoteResponseDto(

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,

        @Schema(description = "최애 이름", example = "LeoJ")
        String favoriteName,

        @Schema(description = "레큐북 제목", example = "1번째 레큐북")
        String title,

        @Schema(description = "레큐노트 고유 id", example = "1")
        Long noteId,

        @Schema(description = "레큐노트 내용", example = "레큐노트 내용입니다.")
        String content,

        @Schema(description = "레큐노트 생성 일시", example = "2024.01.11")
        String noteDate,

        @Schema(description = "레큐노트 텍스트 컬러 번호", example = "#191919")
        String noteTextColor,

        @Schema(description = "레큐노트 배경 (#929DD9 or 이미지 URL(*.jpg))", example = "https://dzfv99wxq6tx0.cloudfront.net/notes/background_image/676c2ca3-f868-423f-8000-a0bcb67dc797.jpg")
        String noteBackground
) {
    public static List<MypageNoteResponseDto> of(List<Note> notes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        List<MypageNoteResponseDto> noteList = notes.stream()
                .sorted(comparing(Note::getCreatedAt).reversed())
                .map(note -> new MypageNoteResponseDto(
                        note.getBook().getUuid(),
                        note.getBook().getFavoriteName(),
                        note.getBook().getTitle(),
                        note.getId(),
                        note.getContent(),
                        note.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                        note.getTextColor(),
                        note.getBackground()
                ))
                .toList();

        return noteList;
    }
}