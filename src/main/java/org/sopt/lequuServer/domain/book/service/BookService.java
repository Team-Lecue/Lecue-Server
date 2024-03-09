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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Book> sortBooksDesc(LocalDateTime startDate, LocalDateTime endDate) {
        return bookRepository.findBookLastMonth(startDate, endDate)
            .stream()
            .sorted(Comparator.comparingInt(book -> -book.getNotes().size())) // -를 붙여 내림차순 정렬
            .collect(Collectors.toList());
    }
    // 레큐노트가 많이 부착된 순서로 레큐북 정렬
}
