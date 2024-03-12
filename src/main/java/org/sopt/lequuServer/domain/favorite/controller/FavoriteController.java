package org.sopt.lequuServer.domain.favorite.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.favorite.dto.request.FavoriteCreateRequestDto;
import org.sopt.lequuServer.domain.favorite.dto.response.FavoriteBookResponseDto;
import org.sopt.lequuServer.domain.favorite.facade.FavoriteFacade;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@SecurityRequirement(name = "JWT Authorization")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController implements FavoriteApi {

    private final FavoriteFacade favoriteFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createFavorite(Principal principal, @RequestBody FavoriteCreateRequestDto request) {
        favoriteFacade.createFavorite(JwtProvider.getUserFromPrincial(principal), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessType.CREATE_FAVORITE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteBookResponseDto>>> getFavorite(Principal principal) {
        return ResponseEntity.ok(ApiResponse.success(SuccessType.GET_FAVORITE_SUCCESS, favoriteFacade.getFavorite(JwtProvider.getUserFromPrincial(principal))));
    }
}
