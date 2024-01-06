package org.sopt.lequuServer.domain.sticker.repository;

import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    @Query("SELECT s FROM Sticker s WHERE s.bookId IN :bookIds")
    List<Sticker> findStickersByBookIds(@Param("bookIds") List<Long> bookIds);
}