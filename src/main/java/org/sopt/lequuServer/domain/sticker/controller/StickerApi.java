package org.sopt.lequuServer.domain.sticker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequestDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponseDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponseDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Tag(name = "Sticker", description = "스티커 API")
@SecurityRequirement(name = "JWT Authorization")
public interface StickerApi {

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "스티커팩 목록 조회에 성공했습니다.",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StickerPackResponseDto.class)))
    )
    @Operation(summary = "스티커팩 목록 불러오기")
    public ApiResponse<List<StickerPackResponseDto>> getStickerPackList(@Schema(example = "1") @PathVariable Long bookId);

    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "스티커 부착에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = StickerPostResponseDto.class))
    )
    @Operation(summary = "스티커 부착하기")
    public ApiResponse<StickerPostResponseDto> postSticker(Principal principal, @RequestBody StickerPostRequestDto request);
}
