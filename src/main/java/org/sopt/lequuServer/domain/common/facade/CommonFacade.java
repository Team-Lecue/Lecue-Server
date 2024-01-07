package org.sopt.lequuServer.domain.common.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonFacade {
    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;

    public SplashDto getSplash() {
        return SplashDto.of(noteRepository.count());
    }

    public List<PopularBookResponseDto> getHome() {
        return bookRepository.getAllByPopular(true, PageRequest.of(0, 6))
                .stream().map(PopularBookResponseDto::of)
                .toList();
    }

}
