package org.sopt.lequuServer.domain.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequestDto;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.facade.BookFacade;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookFacade bookFacade;
    private final BookService bookService;

    @PostMapping
    public ApiResponse<BookCreateResponseDto> createBook(@Valid @RequestBody BookCreateRequestDto request, Principal principal) {
        return ApiResponse.success(SuccessType.BOOK_CREATE_SUCCESS, bookFacade.createBook(request, JwtProvider.getUserFromPrincial(principal)));
    }

    @DeleteMapping("/{bookId}")
    public ApiResponse<?> createBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ApiResponse.success(SuccessType.BOOK_DELETE_SUCCESS);
    }
}