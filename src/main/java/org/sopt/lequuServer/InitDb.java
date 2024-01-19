package org.sopt.lequuServer;

import static org.sopt.lequuServer.domain.sticker.model.StickerCategory.CAT;
import static org.sopt.lequuServer.domain.sticker.model.StickerCategory.CUSTOM;
import static org.sopt.lequuServer.domain.sticker.model.StickerCategory.EVENT;
import static org.sopt.lequuServer.domain.sticker.model.StickerCategory.ITEM;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    private static final List<String> CHARACTER_STICKERS = Arrays.asList(
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/sticker_foryou.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/sticker_love.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012477@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012478@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012484@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012485@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012486@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012489@3x.png",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012491@3x.png"
    );

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Transactional
        public void dbInit() {

            if (!isDatabaseEmpty()) {
                return;
            }

            Member member1 = Member.builder()
                    .socialPlatform(SocialPlatform.KAKAO)
                    .socialId("3251153440")
                    .build();
            member1.updateSocialInfo("레큐", "http://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg");
            member1.updateNickname("양파큐야");
            em.persist(member1);

            for (int i = 4; i < 10; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(CAT)
                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_100001249" + i + "@3x.png")
                        .build();
                em.persist(sticker);
            }

            for (int i = 1; i < 7; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(EVENT)
                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/" + i + "-" + i + ".svg")
                        .build();
                em.persist(sticker);
            }
            for (int i = 1; i < 15; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(CUSTOM)
                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/" + i + ".svg")
                        .build();
                if (i == 6) {
                    sticker = Sticker.builder()
                            .bookId(0L)
                            .category(CUSTOM)
                            .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/" + i + ".png")
                            .build();
                }
                em.persist(sticker);
            }
            Sticker sticker2 = Sticker.builder()
                    .bookId(0L)
                    .category(CUSTOM)
                    .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/Group_1000012465@3x.png")
                    .build();
            em.persist(sticker2);

            for (String url : CHARACTER_STICKERS) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(ITEM)
                        .stickerImage(url)
                        .build();
                em.persist(sticker);
            }
        }

        private boolean isDatabaseEmpty() {
            Long count = em.createQuery("SELECT COUNT(m) FROM Member m", Long.class)
                    .getSingleResult();
            return count == 0;
        }
    }
}