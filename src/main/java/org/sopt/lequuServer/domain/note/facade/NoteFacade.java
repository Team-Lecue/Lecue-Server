package org.sopt.lequuServer.domain.note.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.service.MemberService;
import org.sopt.lequuServer.domain.note.dto.request.NoteCreateDto;
import org.sopt.lequuServer.domain.note.dto.response.NoteResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.service.NoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteFacade {

    private final MemberService memberService;
    private final BookService bookService;
    private final NoteService noteService;

    @Transactional
    public NoteResponseDto createNote(Long userId, NoteCreateDto noteCreateDto) {
        Member member = memberService.getMemberById(userId);
        Book book = bookService.getBookById(noteCreateDto.bookId());

        return NoteResponseDto.of(noteService.saveNote(
                Note.of(noteCreateDto.content(), noteCreateDto.background(), noteCreateDto.textColor(), member, book)));
    }
}
