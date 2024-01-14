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

        @Schema(description = "레큐노트 텍스트 컬러(검정:0, 흰색:1)", example = "0")
        int noteTextColor,

        @Schema(description = "레큐노트 배경 색깔(0~11, 이미지일 경우 -1)", example = "-1")
        int noteBackgroundColor,

        @Schema(description = "레큐노트 배경 이미지 URL(*.jpg, 색깔일 경우 “”)", example = "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/notes/background_image/676c2ca3-f868-423f-8000-a0bcb67dc797.jpg")
        String noteBackgroundImage
) {
    public static NoteDetailResponseDto of(Note note, int renderType, int noteBackgroundColor, String noteBackgroundImage) {
        String noteDate = formatLocalDate(note);

        return new NoteDetailResponseDto(note.getId(), renderType, note.getContent(), noteDate,
                note.getMember().getNickname(), note.getTextColor(), noteBackgroundColor, noteBackgroundImage);
    }

    private static String formatLocalDate(Note note) {
        LocalDateTime createdAt = note.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return createdAt.format(formatter);
    }
}
