package org.sopt.lequuServer.domain.common.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.common.facade.CommonFacade;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/common")
@RequiredArgsConstructor
public class CommonController {

    private final CommonFacade commonFacade;

    @GetMapping("/splash")
    public ApiResponse<SplashDto> getSplash() {
        return ApiResponse.success(SuccessType.GET_SPLASH_SUCCESS, commonFacade.getSplash());
    }

}
