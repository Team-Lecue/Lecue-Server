package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book createBook(Book book) {

        return bookRepository.save(book);
    }

    public Book getBook(Long bookId) {
        return bookRepository.findByIdOrThrow(bookId);
    }
}