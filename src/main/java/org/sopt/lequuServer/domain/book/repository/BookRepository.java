package org.sopt.lequuServer.domain.book.repository;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    default Book getBookById(Long id) {
        return this.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOOK_ERROR));
    }
}