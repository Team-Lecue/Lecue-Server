package org.sopt.lequuServer.domain.sticker.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequestDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponseDto;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponseDto;
import org.sopt.lequuServer.domain.sticker.facade.StickerFacade;
import org.sopt.lequuServer.domain.sticker.service.StickerService;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;

@RestController
@RequestMapping("/api/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final StickerService stickerService;
    private final StickerFacade stickerFacade;

    @GetMapping("/{bookId}")
    public ApiResponse<List<StickerPackResponseDto>> getStickerPackList(@PathVariable Long bookId) {
        return ApiResponse.success(STICKER_PACK_LIST_SUCCESS, stickerService.getStickerPackList(bookId));
    }

    @PostMapping
    public ApiResponse<StickerPostResponseDto> postSticker(Principal principal, @RequestBody StickerPostRequestDto request) {
        return ApiResponse.success(STICKER_POST_SUCCESS, stickerFacade.postSticker(JwtProvider.getUserFromPrincial(principal), request));
    }
}