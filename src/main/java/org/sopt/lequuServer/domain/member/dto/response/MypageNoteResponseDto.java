package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

import java.time.format.DateTimeFormatter;

public record MypageNoteResponseDto(
        String bookUuid,

        String title,

        Long noteId,
        String content,

        String noteDate,

        int noteTextColor,
        int noteBackgroundColor,
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
