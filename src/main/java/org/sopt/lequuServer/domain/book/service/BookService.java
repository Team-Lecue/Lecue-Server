package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.repository.PostedStickerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;
    private final PostedStickerRepository postedStickerRepository;

    @Transactional
    public BookCreateResponseDto createBook(Book book, Member member) {
        member.addBook(book);
        return BookCreateResponseDto.of(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(Long bookId) {

        // bookId가 올바른건지 검증
        Book book = bookRepository.findByIdOrThrow(bookId);

        // 레큐북 id에 속하는 레큐노트 삭제
        List<Note> notes = book.getNotes();
        noteRepository.deleteAllInBatch(notes);

        /** 순회 돌면서 삭제할 때 이용
         for (Note note : notes) {
         note.getId()
         }
         */

        // 레큐북 id에 속하는 붙여진 스티커 삭제
        List<PostedSticker> postedStickers = book.getPostedStickers();
        postedStickerRepository.deleteAllInBatch(postedStickers);

        // 정상적인 book id가 전송되면
        bookRepository.deleteById(bookId);
    }
}
