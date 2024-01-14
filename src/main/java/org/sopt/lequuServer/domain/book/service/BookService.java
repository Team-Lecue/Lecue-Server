package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        log.info("📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝📝\n\n" +
                "- 📝 유저가 새로운 레큐북을 생성했습니다!\n" +
                "- 👀 유저 닉네임: " + member.getNickname() + "\n" +
                "- ⏰ 생성 시간: " + createdBook.getCreatedAt() + "\n" +
                "\n" +
                "- 📝 레큐북 제목: " + createdBook.getTitle() + "\n" +
                "- 💬 레큐북 소개: " + createdBook.getDescription() + "\n" +
                "- 🔗 레큐북 링크: https://www.lecue.me/lecue-book/" + createdBook.getUuid() + "\n" +
                "- ✨ 최애 이름: " + createdBook.getFavoriteName() + "\n" +
                "- 📷 최애 이미지: " + createdBook.getFavoriteImage());

        return BookCreateResponseDto.of(createdBook);
    }
}
