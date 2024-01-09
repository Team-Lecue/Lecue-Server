package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

import java.time.LocalDateTime;

public record MypageNoteListResponseDto(
        String bookUuid,
        String title,
        Long noteId,
        String content,
        LocalDateTime noteDate,
        int noteBackgroundColor,
        String noteBackgroundImage
) {
    public static MypageNoteListResponseDto of(Note note) {

        return new MypageNoteListResponseDto(
                note.getBook().getUuid(),
                note.getBook().getTitle(),
                note.getId(),
                note.getContent(),
                note.getCreatedAt(),
                note.getBackgroundColor(),
                note.getBackgroundImage()
        );
    }
}
