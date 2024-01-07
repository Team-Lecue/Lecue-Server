package org.sopt.lequuServer.domain.sticker.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.sticker.dto.request.StickerPostRequest;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPostResponse;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
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

    private final StickerFacade stickerFacade;

    @GetMapping("/{bookId}")
    public ApiResponse<List<StickerPackResponse>> getStickerPackList(@PathVariable Long bookId) {
        return ApiResponse.success(STICKER_PACK_LIST_SUCCESS, stickerFacade.getStickerPackList(bookId));
    }

    @PostMapping
    public ApiResponse<StickerPostResponse> postSticker(Principal principal, @RequestBody StickerPostRequest request) {
        return ApiResponse.success(STICKER_POST_SUCCESS, stickerFacade.postSticker(JwtProvider.getUserFromPrincial(principal), request));
    }
}