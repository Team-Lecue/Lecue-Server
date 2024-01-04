package org.sopt.lequuServer.domain.book.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookCreateRequest(

        String favoriteName,

        String favoriteImage,

        String title,

        String description,

        int backgroundColor
) {
}
