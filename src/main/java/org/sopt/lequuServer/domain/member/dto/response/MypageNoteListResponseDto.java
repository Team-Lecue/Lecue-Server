package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.note.model.Note;

import java.time.format.DateTimeFormatter;

public record MypageNoteListResponseDto(

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

        @Schema(description = "레큐노트 텍스트 컬러 번호", example = "#FFFFFF")
        String noteTextColor,

        @Schema(description = "레큐노트 배경 (#FFFFFF or 이미지 URL(*.jpg))", example = "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/notes/background_image/676c2ca3-f868-423f-8000-a0bcb67dc797.jpg")
        String noteBackground
) {
    public static MypageNoteListResponseDto of(Note note) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String noteDate = note.getCreatedAt().format(formatter);

        return new MypageNoteListResponseDto(note.getBook().getUuid(), note.getBook().getFavoriteName(), note.getBook().getTitle(),
                note.getId(), note.getContent(), noteDate, note.getTextColor(), note.getBackground());
    }
}
