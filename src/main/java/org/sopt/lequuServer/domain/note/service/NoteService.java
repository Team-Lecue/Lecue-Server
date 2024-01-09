package org.sopt.lequuServer.domain.note.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.note.dto.response.NoteCreateResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public NoteCreateResponseDto saveNote(Note note, Member member, Book book) {

        member.addNote(note);
        book.addNote(note);

        return NoteCreateResponseDto.of(noteRepository.save(note));
    }
}
