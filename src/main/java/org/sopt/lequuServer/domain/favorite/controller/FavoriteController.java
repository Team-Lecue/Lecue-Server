package org.sopt.lequuServer.domain.favorite.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.favorite.dto.request.FavoriteCreateRequestDto;
import org.sopt.lequuServer.domain.favorite.facade.FavoriteFacade;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/favorite")
public class FavoriteController {

    private final FavoriteFacade favoriteFacade;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createFavorite(Principal principal, @RequestBody FavoriteCreateRequestDto request) {
        favoriteFacade.createFavorite(JwtProvider.getUserFromPrincial(principal), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessType.CREATE_FAVORITE_SUCCESS));
    }
}
