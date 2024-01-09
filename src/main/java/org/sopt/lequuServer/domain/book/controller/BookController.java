package org.sopt.lequuServer.domain.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequestDto;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
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
@Tag(name = "Book", description = "레큐 북 API")
@SecurityRequirement(name = "JWT Auth")
public class BookController {

    private final BookFacade bookFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "레큐 북 생성")
    public ApiResponse<BookCreateResponseDto> createBook(@Valid @RequestBody BookCreateRequestDto request, Principal principal) {
        return ApiResponse.success(SuccessType.BOOK_CREATE_SUCCESS, bookFacade.createBook(request, JwtProvider.getUserFromPrincial(principal)));
    }

    @DeleteMapping("/{bookId}")
    @Operation(description = "레큐 북 삭제")
    public ApiResponse<?> deleteBook(@PathVariable Long bookId) {
        bookFacade.deleteBook(bookId);
        return ApiResponse.success(SuccessType.BOOK_DELETE_SUCCESS);
    }
}