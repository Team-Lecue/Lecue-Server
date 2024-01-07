package org.sopt.lequuServer.domain.note.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.note.dto.request.NoteCreateDto;
import org.sopt.lequuServer.domain.note.dto.response.NoteResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.service.NoteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteFacade {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final NoteService noteService;

    public NoteResponseDto createNote(Long userId, NoteCreateDto noteCreateDto) {
        Member member = memberRepository.findByIdOrThrow(userId);
        Book book = bookRepository.findByIdOrThrow(noteCreateDto.bookId());

        return noteService.saveNote(Note.of(noteCreateDto.content(), noteCreateDto.background(), noteCreateDto.textColor(), member, book));
    }
}
