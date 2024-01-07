package org.sopt.lequuServer.domain.note.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

public record NoteResponseDto(
        Long noteId,
        String bookUuid
) {
    public static NoteResponseDto of(Note note) {
        return new NoteResponseDto(note.getId(), note.getBook().getUuid());
    }
}
