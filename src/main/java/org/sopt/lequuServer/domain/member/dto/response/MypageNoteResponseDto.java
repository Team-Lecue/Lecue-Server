package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.note.model.Note;

import java.time.format.DateTimeFormatter;

public record MypageNoteResponseDto(

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid,


        @Schema(description = "레큐북 제목", example = "1번째 레큐북")
        String title,


        @Schema(description = "레큐노트 고유 id", example = "1")
        Long noteId,

        @Schema(description = "레큐노트 내용", example = "레큐노트 내용입니다.")
        String content,

        @Schema(description = "레큐노트 생성 일시", example = "2024.01.11")
        String noteDate,

        @Schema(description = "레큐노트 텍스트 컬러 번호(검정:0, 흰색:1)", example = "0")
        int noteTextColor,

        @Schema(description = "레큐노트 배경 색깔(0~11, 이미지일 경우 -1)", example = "-1")
        int noteBackgroundColor,

        @Schema(description = "레큐노트 배경 이미지 URL(*.jpg, 색깔일 경우 “”)", example = "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/notes/background_image/676c2ca3-f868-423f-8000-a0bcb67dc797.jpg")
        String noteBackgroundImage
) {
    public static MypageNoteResponseDto of(Note note) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String noteDate = note.getCreatedAt().format(formatter);

        String background = note.getBackground();
        if (background.endsWith(".jpg")) {
            return new MypageNoteResponseDto(note.getBook().getUuid(), note.getBook().getTitle(),
                    note.getId(), note.getContent(), noteDate, note.getTextColor(), -1, background);
        }
        return new MypageNoteResponseDto(note.getBook().getUuid(), note.getBook().getTitle(),
                note.getId(), note.getContent(), noteDate, note.getTextColor(), Integer.parseInt(background), "");
    }
}
