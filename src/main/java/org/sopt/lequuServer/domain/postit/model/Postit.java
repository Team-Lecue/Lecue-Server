package org.sopt.lequuServer.domain.postit.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "postit")
public class Postit extends BaseTimeEntity {

    @Id
    @Column(name = "postit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String background;

    private int textColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rolling_paper_id")
    private RollingPaper rollingPaper;

    @Builder
    public Postit(String content, String background, int textColor, User user, RollingPaper rollingPaper) {
        this.content = content;
        this.background = background;
        this.textColor = textColor;
        this.user = user;
        this.rollingPaper = rollingPaper;
    }

    public static Postit of(String content, String background, int textColor, User user, RollingPaper rollingPaper) {
        return new Postit(content, background, textColor, user, rollingPaper);
    }
}