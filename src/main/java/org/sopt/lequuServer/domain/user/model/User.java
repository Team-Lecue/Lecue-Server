package org.sopt.lequuServer.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.postit.model.Postit;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;
import org.sopt.lequuServer.global.sociallogin.SocialPlatform;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    /**
     * 소셜 로그인 관련
     */
    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;

    @Column(nullable = false)
    private String socialId;

    /**
     * 카카오 로그인 한정 제공 정보
     */
    private String socialNickname;

    private String socialProfileImage;

    private String socialAccessToken;

    // 로그인 새롭게 할 때마다 해당 필드들 업데이트
    public void updateSocialInfo(String socialNickname, String socialProfileImage, String socialAccessToken) {
        this.socialNickname = socialNickname;
        this.socialProfileImage = socialProfileImage;
        this.socialAccessToken = socialAccessToken;
    }

    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * 연관 되어 있는 엔티티
     */
    @OneToMany(mappedBy = "user")
    private final List<RollingPaper> rollingPapers = new ArrayList<>();

    public void addRollingPaper(RollingPaper rollingPaper) {
        rollingPapers.add(rollingPaper);
    }

    @OneToMany(mappedBy = "user")
    private final List<Postit> postits = new ArrayList<>();

    public void addPostit(Postit postit) {
        postits.add(postit);
    }

    @OneToMany(mappedBy = "user")
    private final List<PostedSticker> postedStickers = new ArrayList<>();

    public void addPostedSticker(PostedSticker postedSticker) {
        postedStickers.add(postedSticker);
    }

    /**
     * 유저가 최초로 생성될 때 필요한 최소 정보
     */
    public User(SocialPlatform socialPlatform, String socialId) {
        this.socialPlatform = socialPlatform;
        this.socialId = socialId;
    }
}