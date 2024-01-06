package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponse;
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
    public BookCreateResponse createBook(Book book) {

        return BookCreateResponse.of(bookRepository.save(book));
    }
}