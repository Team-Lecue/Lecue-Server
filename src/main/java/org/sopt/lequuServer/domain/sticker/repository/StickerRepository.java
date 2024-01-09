package org.sopt.lequuServer.domain.sticker.repository;

import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.sopt.lequuServer.global.exception.enums.ErrorType;
import org.sopt.lequuServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    @Query("SELECT s FROM Sticker s WHERE s.bookId IN :bookIds")
    List<Sticker> findStickersByBookIds(@Param("bookIds") List<Long> bookIds);
    
    @Modifying
    @Query("DELETE FROM Sticker s WHERE s.bookId = :bookId")
    void deleteStickersByBookId(@Param("bookId") Long bookId);

    default Sticker findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_STICKER_ERROR)
        );
    }
}