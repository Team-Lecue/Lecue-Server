package org.sopt.lequuServer.domain.favorite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.lequuServer.domain.favorite.dto.request.FavoriteRequestDto;
import org.sopt.lequuServer.domain.favorite.dto.response.FavoriteBookResponseDto;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Tag(name = "Favorite", description = "즐겨찾기 API")
public interface FavoriteApi {

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "즐겨찾기 레큐북 등록을 성공했습니다."
    )
    @Operation(summary = "즐겨찾기 레큐북 생성")
    public ResponseEntity<ApiResponse<?>> createFavorite(Principal principal, @RequestBody FavoriteRequestDto request);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "즐겨찾는 레큐북 조회에 성공했습니다.",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FavoriteBookResponseDto.class)))
    )
    @Operation(summary = "즐겨찾는 레큐북 조회")
    public ResponseEntity<ApiResponse<List<FavoriteBookResponseDto>>> getFavorite(Principal principal);

    @SecurityRequirement(name = "JWT Authorization")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "즐겨찾기 레큐북 삭제에 성공했습니다."
    )
    @Operation(summary = "즐겨찾기 레큐북 삭제")
    public ResponseEntity<ApiResponse<?>> deleteFavorite(Principal principal, @RequestBody FavoriteRequestDto request);
}