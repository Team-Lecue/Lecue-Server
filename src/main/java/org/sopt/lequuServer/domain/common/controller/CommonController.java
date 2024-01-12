package org.sopt.lequuServer.domain.common.controller;

import io.sentry.Sentry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.common.dto.response.PopularBookResponseDto;
import org.sopt.lequuServer.domain.common.dto.response.SplashDto;
import org.sopt.lequuServer.domain.common.facade.CommonFacade;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
@Tag(name = "Common", description = "홈 & 스플래시 API")
public class CommonController {

    private final CommonFacade commonFacade;

    @GetMapping("/splash")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "스플래시 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = SplashDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "스플래시 조회")
    public ApiResponse<SplashDto> getSplash() {
        return ApiResponse.success(SuccessType.GET_SPLASH_SUCCESS, commonFacade.getSplash());
    }

    @GetMapping("/home")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "홈 화면 조회에 성공했습니다.",
            content = @Content(schema = @Schema(implementation = PopularBookResponseDto.class))
    )
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "홈 조회")
    public ApiResponse<List<PopularBookResponseDto>> getHome() {
        return ApiResponse.success(SuccessType.GET_HOME_SUCCESS, commonFacade.getHome());
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> sentryTest() {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Sentry.captureException(e);
        }
        return null;
    }
}

