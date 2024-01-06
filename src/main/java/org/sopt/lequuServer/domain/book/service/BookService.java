package org.sopt.lequuServer.domain.book.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getPopularBook() {
        return bookRepository.getAllByPopular(true, PageRequest.of(0, 6));
    }
}
