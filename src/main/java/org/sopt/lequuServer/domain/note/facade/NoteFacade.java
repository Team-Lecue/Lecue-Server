package org.sopt.lequuServer.domain.note.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.note.dto.request.NoteCreateDto;
import org.sopt.lequuServer.domain.note.dto.response.NoteResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.service.NoteService;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.NOTE_BACKGROUND_IMAGE_FOLDER_NAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteFacade {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final NoteService noteService;
    private final S3Service s3Service;

    public NoteResponseDto createNote(Long userId, NoteCreateDto noteCreateDto) {
        Member member = memberRepository.findByIdOrThrow(userId);
        Book book = bookRepository.findByIdOrThrow(noteCreateDto.bookId());

        String background = noteCreateDto.backgroundImages();
        if (background.endsWith(".jpg")) {
            background = s3Service.getURL(NOTE_BACKGROUND_IMAGE_FOLDER_NAME.getValue() + noteCreateDto.backgroundImages());
        }

        return noteService.saveNote(Note.of(noteCreateDto.content(), noteCreateDto.backgroundColor(), noteCreateDto.backgroundImages(), noteCreateDto.textColor(), member, book), member, book);
    }
}
