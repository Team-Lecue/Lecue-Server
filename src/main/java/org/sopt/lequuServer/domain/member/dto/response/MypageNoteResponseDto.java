package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

import java.util.List;

import static java.util.Comparator.comparing;

public record MypageNoteResponseDto(

    List<MypageNotesResponseDto> noteList
) {
    public static MypageNoteResponseDto of(List<Note> notes) {

        List<MypageNotesResponseDto> noteList = notes.stream()
                                                    .sorted(comparing(Note::getCreatedAt).reversed())
                                                    .map(MypageNotesResponseDto::of)
                                                    .toList();

        return new MypageNoteResponseDto(noteList);
    }
}
