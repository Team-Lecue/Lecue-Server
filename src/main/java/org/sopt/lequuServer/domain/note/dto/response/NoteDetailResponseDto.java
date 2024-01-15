package org.sopt.lequuServer.domain.note.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.note.model.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record NoteDetailResponseDto(

        @Schema(description = "레큐노트 고유 id", example = "1")
        Long noteId,

        @Schema(description = "레큐노트가 렌더링 되는 타입", example = "6")
        int renderType,

        @Schema(description = "레큐노트 내용", example = "레큐노트 내용입니다.")
        String content,

        @Schema(description = "레큐노트 생성 일시", example = "2024.01.11")
        String noteDate,

        @Schema(description = "레큐노트 작성자 닉네임", example = "레큐")
        String noteNickname,

        @Schema(description = "레큐노트 텍스트 컬러", example = "#FFFFFF")
        String noteTextColor,

        @Schema(description = "레큐노트 배경 (#FFFFFF or 이미지 URL(*.jpg))", example = "https://dzfv99wxq6tx0.cloudfront.net/notes/background_image/676c2ca3-f868-423f-8000-a0bcb67dc797.jpg")
        String noteBackground
) {
    public static NoteDetailResponseDto of(Note note, int renderType) {
        String noteDate = formatLocalDate(note);

        return new NoteDetailResponseDto(note.getId(), renderType, note.getContent(), noteDate,
                note.getMember().getNickname(), note.getTextColor(), note.getBackground());
    }

    private static String formatLocalDate(Note note) {
        LocalDateTime createdAt = note.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return createdAt.format(formatter);
    }
}
