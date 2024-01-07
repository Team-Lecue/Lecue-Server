package org.sopt.lequuServer.domain.note.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.note.dto.request.NoteCreateDto;
import org.sopt.lequuServer.domain.note.facade.NoteFacade;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
@Tag(name = "Note", description = "레큐 노트 API")
@SecurityRequirement(name = "JWT Auth")
public class NoteController {

    private final NoteFacade noteFacade;

    @Operation(summary = "레큐 노트 생성")
    @PostMapping
    public ApiResponse<?> createNote(Principal principal, NoteCreateDto noteCreateDto) {
        return ApiResponse.success(SuccessType.CREATE_NOTE_SUCCESS, noteFacade.createNote(JwtProvider.getUserFromPrincial(principal), noteCreateDto));
    }
}
