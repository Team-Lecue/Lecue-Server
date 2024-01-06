package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequest;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponse;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookRepository;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.repository.MemberRepository;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.BOOK_FAVORITE_IMAGE_FOLDER_NAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request, Long memberId) {

        /**
         * 요청을 한 유저를 가장 먼저 검증하고
         * 검증이 된 유저에 한해서 동작을 할 수 있도록
         * 로직을 순서대로 작성해야함
         * 청을 한 유저 검증 -> Filter에서 다 해줌
         */

        Member member = memberRepository.findByIdOrThrow(memberId);

        // 유저 검증이 완료된 후에 새로운 Book 객체를 생성할 수 있는 것
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

        return BookCreateResponse.of(bookRepository.save(book));
    }
}