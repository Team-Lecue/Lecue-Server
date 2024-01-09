package org.sopt.lequuServer.domain.note.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

public record NoteCreateResponseDto(
        Long noteId,
        String bookUuid
) {
    public static NoteCreateResponseDto of(Note note) {
        return new NoteCreateResponseDto(note.getId(), note.getBook().getUuid());
    }
}
