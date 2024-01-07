package org.sopt.lequuServer.domain.book.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequest;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponse;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.service.MemberService;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.BOOK_FAVORITE_IMAGE_FOLDER_NAME;

@Service
@RequiredArgsConstructor
public class BookFacade {

    private final BookService bookService;
    private final MemberService memberService;
    private final S3Service s3Service;

    public BookCreateResponse createBook(BookCreateRequest request, Long memberId) {

        // 유저 검증이 완료된 후에 새로운 Book 객체를 생성할 수 있는 것
        Member member = memberService.getMember(memberId);

        /**
         * 요청을 한 유저를 가장 먼저 검증하고
         * 검증이 된 유저에 한해서 동작을 할 수 있도록
         * 로직을 순서대로 작성해야함
         * 요청을 한 유저 검증 -> Filter에서 다 해줌
         */

        // UUID 생성
        String bookUuid = UUID.randomUUID().toString();
        // Presigned URL 이미지 업로드하기 위한 이미지 파일명 가져오기
        String imageUrl = s3Service.getURL(BOOK_FAVORITE_IMAGE_FOLDER_NAME.getValue() + request.favoriteImage());

        Book book = Book.builder()
                .uuid(bookUuid)
                .favoriteName(request.favoriteName())
                .favoriteImage(imageUrl)
                .title(request.title())
                .description(request.description())
                .backgroundColor(request.backgroundColor())
                .member(member)
                .build();

        return BookCreateResponse.of(bookService.createBook(book));
    }
}
