package org.sopt.lequuServer.domain.common.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.note.service.NoteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonFacade {

    private final NoteService noteService;

    public Long getSplash() {
        return noteService.getAllNoteCount();
    }

}
