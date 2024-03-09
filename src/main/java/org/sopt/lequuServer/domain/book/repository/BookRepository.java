package org.sopt.lequuServer.domain.book.repository;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.notes n WHERE n.createdAt >= :startDate AND n.createdAt <= :endDate")
    List<Book> findBookLastMonth(LocalDateTime startDate, LocalDateTime endDate);
    // 오늘 날짜 기준으로 최근 한달간의 레큐노트가 붙여진 레큐북 목록 불러오기

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