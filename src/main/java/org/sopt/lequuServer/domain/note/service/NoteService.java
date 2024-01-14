package org.sopt.lequuServer.domain.note.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.note.dto.response.NoteCreateResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.sopt.lequuServer.global.common.logging.LoggingMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public NoteCreateResponseDto saveNote(Note note, Member member, Book book) {

        member.addNote(note);
        book.addNote(note);

        Note createdNote = noteRepository.save(note);

        log.info(LoggingMessage.noteCreateLogMessage(member, book, createdNote));

        return NoteCreateResponseDto.of(createdNote);
    }
}
