package org.sopt.lequuServer.domain.rollingpaper.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.postit.model.Postit;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rolling_paper")
public class RollingPaper extends BaseTimeEntity {

    @Id
    @Column(name = "rolling_paper_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String favoriteName;

    @Column(nullable = false)
    private String favoriteImage;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private int backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "rollingPaper")
    private List<Postit> postits = new ArrayList<>();

    public void addPostit(Postit postit) {
        postits.add(postit);
    }

    @OneToMany(mappedBy = "rollingPaper")
    private List<PostedSticker> postedStickers = new ArrayList<>();

    public void addPostedSticker(PostedSticker postedSticker) {
        postedStickers.add(postedSticker);
    }

    @Builder
    public RollingPaper(String uuid, String favoriteName, String favoriteImage, String title, String description, int backgroundColor, User user) {
        this.uuid = uuid;
        this.favoriteName = favoriteName;
        this.favoriteImage = favoriteImage;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.user = user;
    }

    public static RollingPaper of(String uuid, String favoriteName, String favoriteImage, String title, String description, int backgroundColor, User user) {
        return new RollingPaper(uuid, favoriteName, favoriteImage, title, description, backgroundColor, user);
    }
}
