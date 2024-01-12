package org.sopt.lequuServer.domain.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record NoteCreateDto(
        @Schema(example = "1")
        Long bookId,

        @Schema(example = "test")
        @NotBlank
        @Size(min = 1, max = 1000, message = "레큐노트 내용은 1자 이상 1000자 이하여야 합니다.")
        String content,

        @Schema(example = "0")
        @Min(value = 0, message = "텍스트 컬러 번호는 0 또는 1이어야 합니다.")
        @Max(value = 1, message = "텍스트 컬러 번호는 0 또는 1이어야 합니다.")
        int textColor,

        @Schema(example = "0 or ~.jpg")
        @Pattern(regexp = "^(0|1[0-1]|.*\\.jpg)$", message = "배경은 0에서 11 사이의 숫자 또는 .jpg로 끝나는 형식이어야 합니다.")
        String background
) {
}
