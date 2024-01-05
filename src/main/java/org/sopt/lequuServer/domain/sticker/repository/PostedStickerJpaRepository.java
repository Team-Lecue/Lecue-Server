package org.sopt.lequuServer.domain.sticker.repository;

import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostedStickerJpaRepository extends JpaRepository<PostedSticker, Long> {
}