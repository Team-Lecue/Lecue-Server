package org.sopt.lequuServer.domain.member.dto.response;

import org.sopt.lequuServer.domain.note.model.Note;

import java.util.List;
import java.util.stream.Collectors;

public record MypageNoteResponseDto(

        List<MypageNoteListResponseDto> mypageNoteList
) {
    public static MypageNoteResponseDto of(List<Note> notes) {
        List<MypageNoteListResponseDto> mypageNoteList = notes.stream()
                .map(note -> new MypageNoteListResponseDto(
                        note.getBook().getUuid(),
                        note.getBook().getTitle(),
                        note.getId(),
                        note.getContent(),
                        note.getCreatedAt(),
                        note.getBackgroundColor(),
                        note.getBackgroundImage()
                ))
                .collect(Collectors.toList());

        return new MypageNoteResponseDto(mypageNoteList);
    }
}
