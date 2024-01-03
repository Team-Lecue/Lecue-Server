package org.sopt.lequuServer.domain.sticker.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolling_paper_id")
    private RollingPaper rollingPaper;

    public Sticker(String stickerImage, StickerCategory category, RollingPaper rollingPaper) {
        this.stickerImage = stickerImage;
        this.category = category;
        this.rollingPaper = rollingPaper;
    }

    public static Sticker of(String stickerImage, StickerCategory category, RollingPaper rollingPaper) {
        return new Sticker(stickerImage, category, rollingPaper);
    }
}