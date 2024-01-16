package org.sopt.lequuServer.domain.note.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.note.dto.request.NoteCreateDto;
import org.sopt.lequuServer.domain.note.facade.NoteFacade;
import org.sopt.lequuServer.global.auth.jwt.JwtProvider;
import org.sopt.lequuServer.global.common.dto.ApiResponse;
import org.sopt.lequuServer.global.exception.enums.SuccessType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController implements NoteApi {

    private final NoteFacade noteFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> createNote(Principal principal, @RequestBody @Valid NoteCreateDto noteCreateDto) {
        return ApiResponse.success(SuccessType.CREATE_NOTE_SUCCESS, noteFacade.createNote(JwtProvider.getUserFromPrincial(principal), noteCreateDto));
    }
}
