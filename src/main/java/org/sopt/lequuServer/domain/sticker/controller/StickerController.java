package org.sopt.lequuServer.domain.sticker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequestDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponseDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponseDto;
import org.sopt.lequuServer.domain.sticker.facade.StickerFacade;
import org.sopt.lequuServer.domain.sticker.service.StickerService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.GET_STICKER_PACK_SUCCESS;
import static org.sopt.lequuServer.global.exception.enums.SuccessType.POST_STICKER_SUCCESS;

@RestController
@RequestMapping("/api/stickers")
@RequiredArgsConstructor
@Tag(name = "Sticker", description = "스티커 API")
@SecurityRequirement(name = "JWT Authorization")
public class StickerController {

    private final StickerService stickerService;
    private final StickerFacade stickerFacade;

    @GetMapping("/{bookId}")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "스티커팩 목록 조회에 성공했습니다.",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StickerPackResponseDto.class)))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "스티커팩 목록 불러오기")
    public ApiResponse<List<StickerPackResponseDto>> getStickerPackList(@Schema(example = "1") @PathVariable Long bookId) {
        return ApiResponse.success(GET_STICKER_PACK_SUCCESS, stickerService.getStickerPackList(bookId));
    }

    @PostMapping
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "스티커 부착에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = StickerPostResponseDto.class))
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "스티커 부착하기")
    public ApiResponse<StickerPostResponseDto> postSticker(Principal principal, @RequestBody StickerPostRequestDto request) {
        return ApiResponse.success(POST_STICKER_SUCCESS, stickerFacade.postSticker(JwtProvider.getUserFromPrincial(principal), request));
    }
}