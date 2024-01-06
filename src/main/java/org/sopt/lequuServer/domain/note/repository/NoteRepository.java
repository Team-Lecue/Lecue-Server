package org.sopt.lequuServer.domain.note.repository;

import org.sopt.lequuServer.domain.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
