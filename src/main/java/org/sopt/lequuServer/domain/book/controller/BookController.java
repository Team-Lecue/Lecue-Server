package org.sopt.lequuServer.domain.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequestDto;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.dto.response.BookDetailResponseDto;
import org.sopt.lequuServer.domain.book.facade.BookFacade;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController implements BookApi {

    private final BookFacade bookFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BookCreateResponseDto> createBook(@Valid @RequestBody BookCreateRequestDto request, Principal principal) {
        return ApiResponse.success(SuccessType.CREATE_BOOK_SUCCESS, bookFacade.createBook(request, JwtProvider.getUserFromPrincial(principal)));
    }

    @DeleteMapping("/{bookId}")
    public ApiResponse<?> deleteBook(@PathVariable Long bookId) {
        bookFacade.deleteBook(bookId);
        return ApiResponse.success(SuccessType.DELETE_BOOK_SUCCESS);
    }

    @GetMapping("/detail/{bookUuid}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<BookDetailResponseDto> getBookDetail(@PathVariable String bookUuid) {
        return ApiResponse.success(SuccessType.GET_BOOK_DETAIL_SUCCESS, bookFacade.getBookDetail(bookUuid));
    }
}