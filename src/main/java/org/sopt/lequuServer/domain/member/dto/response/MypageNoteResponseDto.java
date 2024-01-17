package org.sopt.lequuServer.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.lequuServer.domain.note.model.Note;

import java.util.List;

import static java.util.Comparator.comparing;

public record MypageNoteResponseDto(

        @Schema(description = "유저 닉네임", example = "레큐")
        String memberNickname,

        List<MypageNotesResponseDto> noteList
) {
    public static MypageNoteResponseDto of(String nickName, List<Note> notes) {

        List<MypageNotesResponseDto> noteList = notes.stream()
                .sorted(comparing(Note::getCreatedAt).reversed())
                .map(MypageNotesResponseDto::of)
                .toList();

        return new MypageNoteResponseDto(nickName, noteList);
    }
}
