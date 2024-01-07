package org.sopt.lequuServer.domain.note.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    //TODO 되는지 검증 필요
    public Long getAllNoteCount() {
        return noteRepository.count();
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }
}
