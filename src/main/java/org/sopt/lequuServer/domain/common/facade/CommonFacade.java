package org.sopt.lequuServer.domain.common.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonFacade {
    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;
    private final BookService bookService;

    public SplashDto getSplash() {
        return SplashDto.of(noteRepository.count());
    }

    public List<PopularBookResponseDto> getHome() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(1);

        return bookService.sortBooksDesc(startDate, endDate)
            .stream().map(PopularBookResponseDto::of)
            .toList();
    }
    // 레큐북 정렬된 것을 가져오기
}
