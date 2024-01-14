package org.sopt.lequuServer.domain.book.facade;

import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.BOOK_FAVORITE_IMAGE_FOLDER_NAME;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequestDto;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.dto.response.BookDetailResponseDto;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.repository.PostedStickerRepository;
import org.sopt.lequuServer.domain.sticker.repository.StickerRepository;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookFacade {

    private final BookService bookService;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;
    private final StickerRepository stickerRepository;
    private final PostedStickerRepository postedStickerRepository;
    private final S3Service s3Service;

    public BookCreateResponseDto createBook(BookCreateRequestDto request, Long memberId) {

        // 유저 검증이 완료된 후에 새로운 Book 객체를 생성할 수 있는 것
        Member member = memberRepository.findByIdOrThrow(memberId);

        /**
         * 요청을 한 유저를 가장 먼저 검증하고
         * 검증이 된 유저에 한해서 동작을 할 수 있도록
         * 로직을 순서대로 작성해야함
         * 요청을 한 유저 검증 -> Filter에서 다 해줌
         */

        // UUID 생성
        String bookUuid = UUID.randomUUID().toString();
        // Presigned URL 이미지 업로드하기 위한 이미지 파일명 가져오기
//        String imageUrl = s3Service.getURL(BOOK_FAVORITE_IMAGE_FOLDER_NAME.getValue() + request.favoriteImage());
        String imageUrl = s3Service.getCloudFrontURL(
                BOOK_FAVORITE_IMAGE_FOLDER_NAME.getValue() + request.favoriteImage());

        Book book = Book.builder()
                .uuid(bookUuid)
                .favoriteName(request.favoriteName())
                .favoriteImage(imageUrl)
                .title(request.title())
                .description(request.description())
                .backgroundColor(request.backgroundColor())
                .member(member)
                .build();

        return bookService.createBook(book, member);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        // bookId가 올바른건지 검증
        Book book = bookRepository.findByIdOrThrow(bookId);

        // 레큐북 id에 속하는 레큐노트 삭제
        List<Note> notes = book.getNotes();
        noteRepository.deleteAllInBatch(notes);

        /** 순회 돌면서 삭제할 때 이용
         for (Note note : notes) {
         note.getId()
         }
         */

        // 레큐북 id에 속하는 붙여진 스티커 삭제
        List<PostedSticker> postedStickers = book.getPostedStickers();
        postedStickerRepository.deleteAllInBatch(postedStickers);

        // 레큐북 id에 속하는 스티커 삭제
        stickerRepository.deleteStickersByBookId(bookId);

        // 정상적인 book id가 전송되면
        bookRepository.deleteById(bookId);
    }

    public BookDetailResponseDto getBookDetail(String bookUuid) {
        Book book = bookRepository.findByUuidOrThrow(bookUuid);

        return BookDetailResponseDto.of(book);
    }
}
