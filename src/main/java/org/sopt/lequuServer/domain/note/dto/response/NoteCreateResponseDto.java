package org.sopt.lequuServer.domain.note.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.note.model.Note;

public record NoteCreateResponseDto(

        @Schema(description = "레큐노트 고유 id", example = "1")
        Long noteId,

        @Schema(description = "레큐북 UUID", example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
        String bookUuid
) {
    public static NoteCreateResponseDto of(Note note) {
        return new NoteCreateResponseDto(note.getId(), note.getBook().getUuid());
    }
}
