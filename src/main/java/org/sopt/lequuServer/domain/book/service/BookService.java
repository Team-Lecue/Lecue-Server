package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequest;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponse;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.book.repository.BookJpaRepository;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.domain.user.repository.UserJpaRepository;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.sopt.lequuServer.global.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.sopt.lequuServer.global.exception.enums.ErrorType.INVALID_USER;
import static org.sopt.lequuServer.global.exception.enums.ErrorType.NOT_FOUND_BOOK_ID;
import static org.sopt.lequuServer.global.s3.enums.ImageFolderName.BOOK_FAVORITE_IMAGE_FOLDER_NAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookJpaRepository bookJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final S3Service s3Service;
    private final JwtProvider jwtProvider;

    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request, String authorization) {

        /**
         * 요청을 한 유저를 가장 먼저 검증하고
         * 검증이 된 유저에 한해서 동작을 할 수 있도록
         * 로직을 순서대로 작성해야함
         * 청을 한 유저 검증 -> Filter에서 다 해줌
         */

        // Bearer을 제외한 토큰 string만 뺴오는곳
        String token = authorization.split(" ")[1];   // Bearer는 [0]에 담긴 것, 토큰 스트링이 [1]에 담긴 것

        // 유효한 토큰이 가지고 있는 유저 정보가 올바른지
        Long userId = jwtProvider.getUserFromJwt(token);
        Optional<User> findById = userJpaRepository.findById(userId);

        // 전송된 토큰으로 식별되는 유저가 없을 경우
        if (findById.isEmpty()) {
            throw new CustomException(INVALID_USER);
        }

        // optional 객체 안에 있는 실제 User 객체를 꺼냄
        User user = findById.get();

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
                .user(user)
                .build();

        Book saveBook = bookJpaRepository.save(book);   // save된 객체를 가져와야 객체의 id를 받아올 수 있음

        return BookCreateResponse.of(saveBook);
    }

}