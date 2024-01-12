package org.sopt.lequuServer.domain.common.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonFacade {
    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;

    public SplashDto getSplash() {
        return SplashDto.of(noteRepository.count());
    }

    public List<PopularBookResponseDto> getHome() {
        return bookRepository.findAllOrderByPopular()
                .stream().map(PopularBookResponseDto::of)
                .toList();
    }
}
