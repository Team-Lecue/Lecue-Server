package org.sopt.lequuServer.domain.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record NoteCreateDto(
        @Schema(example = "1")
        Long bookId,
        //TODO DB상에서 관리하려면 그냥 Auditing으로 하는게 좋을지도?
        @Schema(example = "2023.12.27 (수)")
        String noteDate,
        @Schema(example = "test")
        String content,
        @Schema(example = "0")
        int textColor,
        @Schema(example = "0")
        String background
) {
}
