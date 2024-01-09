package org.sopt.lequuServer.domain.note.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

public record NoteDetailResponseDto(
        Long noteId,
        int renderType,
        String content,
        String noteDate,
        String noteNickname,
        int noteBackgroundColor,
        String noteBackgroundImage
) {
    public static NoteDetailResponseDto of(Note note, int renderType) {
        String noteDate = note.getCreatedAt();
        int noteBackgroundColor;
        String noteBackgroundImage;
        return new NoteDetailResponseDto(note.getId(), renderType, note.getContent(), noteDate,
                note.getMember().getNickname(), noteBackgroundColor, noteBackgroundImage)
    }
}
