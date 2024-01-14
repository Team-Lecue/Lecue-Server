package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.global.common.logging.LoggingMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookCreateResponseDto createBook(Book book, Member member) {
        member.addBook(book);
        Book createdBook = bookRepository.save(book);

        log.info(LoggingMessage.bookCreateLogMessage(member, createdBook));

        return BookCreateResponseDto.of(createdBook);
    }
}
