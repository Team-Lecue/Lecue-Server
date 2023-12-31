package org.sopt.lequuServer.domain.sticker.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "sticker")
public class Sticker extends BaseTimeEntity {

    @Id
    @Column(name = "sticker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String stickerImage;

    @Column(nullable = false)
    private StickerCategory category;

    @ManyToOne
    @JoinColumn(name = "rolling_paper_id")
    private RollingPaper rollingPaper;
}