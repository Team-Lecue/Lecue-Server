package org.sopt.lequuServer.domain.note.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.note.dto.request.NoteCreateDto;
import org.sopt.lequuServer.domain.note.dto.response.NoteCreateResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.service.NoteService;
import org.sopt.lequuServer.global.BadWordFilterService;
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
    private final BadWordFilterService badWordFilterService;

    @Transactional
    public NoteCreateResponseDto createNote(Long memberId, NoteCreateDto request) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        Book book = bookRepository.findByIdOrThrow(request.bookId());

        String background = request.background();
        if (background.endsWith(".jpg")) {
//            background = s3Service.getURL(NOTE_BACKGROUND_IMAGE_FOLDER_NAME.getValue() + noteCreateDto.background());
            background = s3Service.getCloudFrontURL(NOTE_BACKGROUND_IMAGE_FOLDER_NAME.getValue() + request.background());
        }

        return noteService.saveNote(Note.of(badWordFilterService.badWordChange(memberId, request.content()), background, request.textColor(), member, book), member, book);
    }
}
