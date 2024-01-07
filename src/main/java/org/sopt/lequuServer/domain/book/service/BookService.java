package org.sopt.lequuServer.domain.book.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
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

    public List<Book> getPopularBook() {
        return bookRepository.getAllByPopular(true, PageRequest.of(0, 6));
    }
}

    @Transactional
    public BookCreateResponse createBook(Book book) {
        return BookCreateResponse.of(bookRepository.save(book));
    }
}