package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

import java.time.format.DateTimeFormatter;

public record MypageNoteListResponseDto(
        String bookUuid,
        String title,
        Long noteId,
        String content,
        String noteDate,

        int noteBackgroundColor,
        String noteBackgroundImage
) {
    public static MypageNoteListResponseDto of(Note note) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String noteDate = note.getCreatedAt().format(formatter);

        String background = note.getBackground();
        if (background.endsWith(".jpg")) {
            return new MypageNoteListResponseDto(note.getBook().getUuid(), note.getBook().getTitle(),
                    note.getId(), note.getContent(),
                    noteDate, -1, background);
        } else {
            return new MypageNoteListResponseDto(note.getBook().getUuid(), note.getBook().getTitle(),
                    note.getId(), note.getContent(),
                    noteDate, Integer.parseInt(background), "");
        }
    }
}
