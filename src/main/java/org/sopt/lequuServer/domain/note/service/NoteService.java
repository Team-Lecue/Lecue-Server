package org.sopt.lequuServer.domain.note.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.note.dto.response.NoteCreateResponseDto;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.note.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional
    public NoteCreateResponseDto saveNote(Note note, Member member, Book book) {

        member.addNote(note);
        book.addNote(note);

        Note createdNote = noteRepository.save(note);

        log.info("💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌 💌\n\n" +
                "- 💌 유저가 새로운 레큐노트를 남겼습니다!\n" +
                "- 👀 유저 닉네임: " + member.getNickname() + "\n" +
                "- ⏰ 남긴 시간: " + createdNote.getCreatedAt() + "\n" +
                "\n" +
                "- 🔗 레큐북 링크: https://www.lecue.me/lecue-book/" + book.getUuid() + "\n" +
                "- 📝 레큐북 제목: " + book.getTitle() + "\n" +
                "- 💬 레큐노트 내용: " + createdNote.getContent() + "\n");

        return NoteCreateResponseDto.of(createdNote);
    }
}
