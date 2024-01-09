package org.sopt.lequuServer.domain.sticker.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sticker")
public class Sticker extends BaseTimeEntity {

    @Id
    @Column(name = "sticker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stickerImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StickerCategory category;

    private Long bookId;

    @Builder
    public Sticker(String stickerImage, StickerCategory category, Long bookId) {
        this.stickerImage = stickerImage;
        this.category = category;
        this.bookId = bookId;
    }

    public static Sticker of(String stickerImage, StickerCategory category, Long bookId) {
        return new Sticker(stickerImage, category, bookId);
    }
}