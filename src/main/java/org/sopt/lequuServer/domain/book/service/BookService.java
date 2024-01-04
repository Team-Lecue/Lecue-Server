package org.sopt.lequuServer.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.dto.request.BookCreateRequest;
import org.sopt.lequuServer.domain.book.dto.response.BookCreateResponse;
import org.sopt.lequuServer.domain.book.repository.BookJpaRepository;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.domain.user.repository.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookJpaRepository bookJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request) throws IOException {

    }

}