package org.sopt.lequuServer.domain.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoteCreateDto(
        @Schema(example = "1")
        Long bookId,
        //TODO DB상에서 관리하려면 그냥 Auditing으로 하는게 좋을지도?
        @Schema(example = "2023.12.27 (수)")
        String noteDate,
        //TODO validation 추가
        @Schema(example = "test")
        @NotBlank
        @Size(min = 1, max = 1000, message = "레큐노트 내용은 1자 이상 1000자 이하여야합니다.")
        String content,
        @Schema(example = "0")
        int textColor,
        @Schema(example = "0 or ~.jpg")
        String background
) {
}
