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
        int noteBackgroundColor,
        String noteBackgroundImage
) {
    public static NoteDetailResponseDto of(Note note, int renderType, int noteBackgroundColor, String noteBackgroundImage) {
        LocalDateTime createdAt = note.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String noteDate = createdAt.format(formatter);

        return new NoteDetailResponseDto(note.getId(), renderType, note.getContent(), noteDate,
                note.getMember().getNickname(), noteBackgroundColor, noteBackgroundImage);
    }
}
