package org.sopt.lequuServer.domain.book.repository;

import java.util.List;
import org.sopt.lequuServer.domain.book.model.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select b from Book  b where b.isPopular = :isPopular")
    List<Book> getAllByPopular(@Param("isPopular") Boolean isPopular, PageRequest pageRequest);
}