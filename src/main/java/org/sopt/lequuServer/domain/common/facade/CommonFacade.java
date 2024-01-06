package org.sopt.lequuServer.domain.common.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.note.service.NoteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonFacade {

    private final NoteService noteService;
    private final BookService bookService;

    public SplashDto getSplash() {
        return SplashDto.of(noteService.getAllNoteCount());
    }

    public List<PopularBookResponseDto> getHome() {
        return bookService.getPopularBook().stream().map(PopularBookResponseDto::of).toList();
    }

}
