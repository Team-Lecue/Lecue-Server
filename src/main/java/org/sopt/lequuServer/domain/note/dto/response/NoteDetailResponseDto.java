package org.sopt.lequuServer.domain.note.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record NoteDetailResponseDto(
        Long noteId,
        int renderType,
        String content,
        String noteDate,
        String noteNickname,
        int noteTextColor,
        int noteBackgroundColor,
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
