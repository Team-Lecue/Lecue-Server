package org.sopt.lequuServer.domain.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequest;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponse;
import org.sopt.lequuServer.domain.book.service.BookService;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ApiResponse<BookCreateResponse> createBook(@Valid @RequestBody BookCreateRequest request, @RequestHeader("Authorization") String authorization) {
        return ApiResponse.success(SuccessType.BOOK_CREATE_SUCCESS, bookService.createBook(request, authorization));
    }

}