package org.sopt.lequuServer.domain.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Book", description = "레큐북 API")
public class BookController {

    private final BookFacade bookFacade;

    @PostMapping
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "레큐북이 성공적으로 생성됐습니다.",
            content = @Content(schema = @Schema(implementation = BookCreateResponseDto.class))
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "레큐북 생성")
    public ApiResponse<BookCreateResponseDto> createBook(@Valid @RequestBody BookCreateRequestDto request, Principal principal) {
        return ApiResponse.success(SuccessType.CREATE_BOOK_SUCCESS, bookFacade.createBook(request, JwtProvider.getUserFromPrincial(principal)));
    }

    @DeleteMapping("/{bookId}")
    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "레큐북을 성공적으로 삭제했습니다."
    )
    @Operation(summary = "레큐북 삭제")
    public ApiResponse<?> deleteBook(@Schema(example = "1") @PathVariable Long bookId) {
        bookFacade.deleteBook(bookId);
        return ApiResponse.success(SuccessType.DELETE_BOOK_SUCCESS);
    }

    @GetMapping("/detail/{bookUuid}")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "레큐북 상세 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = BookDetailResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "레큐북 상세 조회")
    public ApiResponse<BookDetailResponseDto> getBookDetail(@Schema(example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021") @PathVariable String bookUuid) {
        return ApiResponse.success(SuccessType.GET_BOOK_DETAIL_SUCCESS, bookFacade.getBookDetail(bookUuid));
    }
}