package org.sopt.lequuServer.domain.note.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.note.dto.response.NoteResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public NoteResponseDto saveNote(Note note) {
        return NoteResponseDto.of(noteRepository.save(note));
    }
}
