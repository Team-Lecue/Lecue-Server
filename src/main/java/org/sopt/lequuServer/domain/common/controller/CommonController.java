package org.sopt.lequuServer.domain.common.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.common.facade.CommonFacade;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class CommonController implements CommonApi {

    private final CommonFacade commonFacade;

    @GetMapping("/splash")
    public ResponseEntity<ApiResponse<SplashDto>> getSplash() {
        return ResponseEntity.ok(ApiResponse.success(SuccessType.GET_SPLASH_SUCCESS, commonFacade.getSplash()));
    }

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<List<PopularBookResponseDto>>> getHome() {
        return ResponseEntity.ok(ApiResponse.success(SuccessType.GET_HOME_SUCCESS, commonFacade.getHome()));
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<?>> test() {
        throw new RuntimeException("테스트용 에러 발생");
    }
}