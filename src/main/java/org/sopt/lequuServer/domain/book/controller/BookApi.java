package org.sopt.lequuServer.domain.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequestDto;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponseDto;
import org.sopt.lequuServer.domain.book.dto.response.BookDetailResponseDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Tag(name = "Book", description = "레큐북 API")
public interface BookApi {

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "레큐북이 성공적으로 생성됐습니다.",
            content = @Content(schema = @Schema(implementation = BookCreateResponseDto.class))
    )
    @Operation(summary = "레큐북 생성")
    public ResponseEntity<ApiResponse<BookCreateResponseDto>> createBook(@Valid @RequestBody BookCreateRequestDto request, Principal principal);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "레큐북을 성공적으로 삭제했습니다."
    )
    @Operation(summary = "레큐북 삭제")
    public ResponseEntity<?> deleteBook(@Schema(example = "1") @PathVariable Long bookId);

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "레큐북 상세 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = BookDetailResponseDto.class))
    )
    @Operation(summary = "레큐북 상세 조회")
    public ResponseEntity<ApiResponse<BookDetailResponseDto>> getBookDetail(@Schema(example = "ee4f66f9-9cf4-4b28-90f4-f71d0ecba021") @PathVariable String bookUuid);
}
