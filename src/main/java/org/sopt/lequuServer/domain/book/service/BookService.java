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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
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

    // 정해진 기간의 레큐노트를 가져와 많이 부착된 순서로 레큐북 정렬하는 로직
    public List<Book> sortBooksDesc(LocalDateTime startDate, LocalDateTime endDate) {
        return bookRepository.findBooksMonth(startDate, endDate)
            .stream()
            .sorted(Comparator.comparingInt(book -> -book.getNotes().size())) // -를 붙여 내림차순 정렬
            .collect(Collectors.toList());
    }

    // 최근 3달을 확인하는데 최근 순으로 먼저 인기레큐북에 추가할 수 있는 로직
    // 현재를 0이라 하면 -1 ~ 0(최근 한달), -2 ~ -1(지지난달 부터 지난달), -3 ~ -2(지지지난달 부터 지지난달)
    public List<Book> getPopularBooks() {
        List<Book> popularBooks = new ArrayList<>();
        LocalDateTime today = LocalDateTime.now();

        // 최근 3달 확인
        for (int i = 0; i < 3; i++) {
            LocalDateTime endDate = today.minusMonths(i);
            LocalDateTime startDate = endDate.minusMonths(1);
            List<Book> books = sortBooksDesc(startDate, endDate);

            if (popularBooks.size() + books.size() <= 6) {  // 만약 현재있는 인기북과 가져온 북이 6이하면 인기북에 가져온 것 다 추가
                popularBooks.addAll(duplicateBook(books, popularBooks));
                continue;
            }

            int remain = 6 - popularBooks.size();
            popularBooks.addAll(duplicateBook((books.subList(0, remain)), popularBooks)); // 예를 들어 인기북에 2개가 있으면 4개만 더 추가하면 되니까 가져온 북의 인덱스 0부터 3까지만 가져오도록 함
            break;
        } // 인기북이 6개 이상이 되거나 최근 3달을 다 검사했으면 for문을 빠져나옴

        return popularBooks;
    }

    // 인기레큐북에 중복된 레큐북을 방지하기 위한 로직
    public List<Book> duplicateBook(List<Book> books, List<Book> popularBooks) {
        return books.stream().filter(o -> popularBooks.stream()
            .noneMatch(Predicate.isEqual(o))).collect(Collectors.toList());
    }
}