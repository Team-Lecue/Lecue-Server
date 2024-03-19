package org.sopt.lequuServer.domain.book.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.notes n WHERE n.createdAt >= :startDate AND n.createdAt <= :endDate")
    List<Book> findBooksMonth(LocalDateTime startDate, LocalDateTime endDate);
    // 정해진 기간의 레큐노트가 붙여진 레큐북 목록 불러오기

    default Book findByIdOrThrow(Long id) {
        return this.findById(id).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_BOOK_ERROR));
    }

    @Query("select b from Book b where b.member.id = :memberId")
    List<Book> findByMemberId(@Param("memberId") Long id);

    Optional<Book> findByUuid(String uuid);

    default Book findByUuidOrThrow(String uuid) {
        return this.findByUuid(uuid).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_BOOK_ERROR));
    }
}