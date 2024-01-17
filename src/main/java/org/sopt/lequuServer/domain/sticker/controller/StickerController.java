package org.sopt.lequuServer.domain.sticker.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequestDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPacksResponseDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponseDto;
import org.sopt.lequuServer.domain.sticker.facade.StickerFacade;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.GET_STICKER_PACK_SUCCESS;
import static org.sopt.lequuServer.global.exception.enums.SuccessType.POST_STICKER_SUCCESS;

@RestController
@RequestMapping("/api/stickers")
@RequiredArgsConstructor
public class StickerController implements StickerApi {

    private final StickerFacade stickerFacade;

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<StickerPacksResponseDto>> getStickerPackList(@PathVariable Long bookId) {
        return ResponseEntity.ok(ApiResponse.success(GET_STICKER_PACK_SUCCESS, stickerFacade.getStickerPackList(bookId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StickerPostResponseDto>> postSticker(Principal principal, @RequestBody StickerPostRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(POST_STICKER_SUCCESS, stickerFacade.postSticker(JwtProvider.getUserFromPrincial(principal), request)));
    }
}