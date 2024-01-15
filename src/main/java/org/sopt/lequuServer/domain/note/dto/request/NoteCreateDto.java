package org.sopt.lequuServer.domain.note.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record NoteCreateDto(
        @Schema(example = "1")
        Long bookId,

        @Schema(example = "레큐화이팅")
        @NotBlank
        @Size(min = 1, max = 1000, message = "레큐노트 내용은 1자 이상 1000자 이하여야 합니다.")
        String content,

        @Schema(description = "텍스트 컬러 번호(#FFFFFF)", example = "#FFFFFF")
        @Pattern(regexp = "^(#[A-Za-z0-9]{3,6})$", message = "색상은 #으로 시작하는 3~6 사이의 색깔코드여야 합니다.")
        String textColor,

        @Schema(description = "레큐노트 배경 색깔 (#FFFFFF) or 이미지 제목 (*.jpg)", example = "469456ec-5894-4014-8b90-332d453217ba.jpg")
        @Pattern(regexp = "^(#[A-Za-z0-9]{3,6}|.*\\.jpg)$", message = "배경은 #으로 시작하는 3~6 사이의 색깔코드 또는 .jpg로 끝나는 형식이어야 합니다.")
        String background
) {
}
