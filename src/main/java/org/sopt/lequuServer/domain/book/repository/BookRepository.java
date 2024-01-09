package org.sopt.lequuServer.domain.book.repository;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT b FROM Book b ORDER BY b.popularRate ASC")
    List<Book> getAllByPopular(PageRequest pageRequest);

    default Book findByIdOrThrow(Long id) {
        return this.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOOK_ERROR));
    }

    Optional<Book> findByUuid(String uuid);

    default Book findByUuidOrThrow(String uuid) {
        return this.findByUuid(uuid).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_BOOK_ERROR));
    }
}