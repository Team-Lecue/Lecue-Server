package org.sopt.lequuServer.domain.sticker.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.sticker.dto.response.StickerPackResponse;
import org.sopt.lequuServer.domain.sticker.service.StickerService;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.sopt.lequuServer.global.exception.enums.SuccessType.*;

@RestController
@RequestMapping("/stickers")
@RequiredArgsConstructor
public class StickerController {

    private final StickerService stickerService;

    @GetMapping("/{bookId}")
    public ApiResponse<List<StickerPackResponse>> getStickerPackList(@PathVariable Long bookId) {
        return ApiResponse.success(STICKER_PACK_LIST_SUCCESS, stickerService.getStickerPackList(bookId));
    }
}