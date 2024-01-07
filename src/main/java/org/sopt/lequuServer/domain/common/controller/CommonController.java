package org.sopt.lequuServer.domain.common.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.common.facade.CommonFacade;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class CommonController {

    private final CommonFacade commonFacade;

    @GetMapping("/splash")
    public ApiResponse<SplashDto> getSplash() {
        return ApiResponse.success(SuccessType.GET_SPLASH_SUCCESS, commonFacade.getSplash());
    }

    @GetMapping("/home")
    public ApiResponse<List<PopularBookResponseDto>> getHome() {
        return ApiResponse.success(SuccessType.GET_HOME_SUCCESS, commonFacade.getHome());
    }
}

