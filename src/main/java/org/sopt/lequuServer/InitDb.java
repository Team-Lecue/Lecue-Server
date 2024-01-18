package org.sopt.lequuServer;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.domain.note.model.Note;
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
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/character_1.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/character_2.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/character_3.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/character_4.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/character_5.svg",
            "https://dzfv99wxq6tx0.cloudfront.net/stickers/character_6.svg"
    );

    private static final List<String> BACKGROUND_COLORS_1 = Arrays.asList(
            "#EFB6B6", "#E5E2CE", "#F8E99A", "#85CEAF"
    );

    private static final List<String> BACKGROUND_COLORS_2 = Arrays.asList(
            "#B3CBE8", "#929DD9", "#FE394C", "#9852F9"
    );

    private static final List<String> BACKGROUND_COLORS_3 = Arrays.asList(
            "#FFD600", "#98ED4D", "#FF71B3"
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

            Book book1 = Book.builder()
                    .uuid("ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
                    .favoriteName("현예진")
                    .favoriteImage("https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/yejin.jpg")
                    .title("현예진의 욕설 필터링 구현을 응원해주세요!")
                    .description("걍 알아서 응원하삼")
                    .backgroundColor("#F5F5F5")
                    .member(member1)
                    .popularRate(0)
                    .build();

            em.persist(book1);
            for (int i = 1; i < 7; i++) {
                Book book = Book.builder()
                        .uuid("ee4f66f9-9cf4-4b28-90f4-f71d0ecba02" + String.valueOf(i + 1))
                        .favoriteName("현예진")
                        .favoriteImage("https://dzfv99wxq6tx0.cloudfront.net/books/favorite_image/yejin.jpg")
                        .title(String.valueOf(i + 1) + "번째 레큐북")
                        .description("레큐북의 내용입니다!")
                        .backgroundColor("#F5F5F5")
                        .member(member1)
                        .popularRate(i)
                        .build();
                em.persist(book);
            }

            for (String background : BACKGROUND_COLORS_1) {
                Note note = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라")
                        .background(background)
                        .textColor("#191919")
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note);
            }
            for (String background : BACKGROUND_COLORS_2) {
                Note note = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라")
                        .background(background)
                        .textColor("#191919")
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note);
            }
            for (int i = 0; i < 2; i++) {
                Note note = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 아마 이건 이미지 위에 보이고 있겠죠??")
                        .background("https://dzfv99wxq6tx0.cloudfront.net/notes/background_image/469456ec-5894-4014-8b90-332d453217ba.jpg")
                        .textColor("#191919")
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note);
            }
            for (String background : BACKGROUND_COLORS_3) {
                Note note1 = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라")
                        .background(background)
                        .textColor("#191919")
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note1);

                Note note2 = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 아마 이건 이미지 위에 보이고 있겠죠??")
                        .background("https://dzfv99wxq6tx0.cloudfront.net/notes/background_image/469456ec-5894-4014-8b90-332d453217ba.jpg")
                        .textColor("#191919")
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note2);
            }

//            Sticker sticker1 = Sticker.builder()
//                    .bookId(0L)
//                    .category(BIRTHDAY)
//                    .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/birth_1.svg")
//                    .build();
//            em.persist(sticker1);
//            for (int i = 0; i < 2; i++) {
//                Sticker sticker = Sticker.builder()
//                        .bookId(0L)
//                        .category(ALPHABET)
//                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
//                        .build();
//                em.persist(sticker);
//            }
//            for (int i = 0; i < 2; i++) {
//                Sticker sticker = Sticker.builder()
//                        .bookId(0L)
//                        .category(BIRTHDAY)
//                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/birth_1.svg")
//                        .build();
//                em.persist(sticker);
//            }
//            for (String characterSticker : CHARACTER_STICKERS) {
//                Sticker sticker = Sticker.builder()
//                        .bookId(0L)
//                        .category(CHARACTER)
//                        .stickerImage(characterSticker)
//                        .build();
//                em.persist(sticker);
//            }
//
//            for (int i = 0; i < 2; i++) {
//                Sticker sticker = Sticker.builder()
//                        .bookId(1L)
//                        .category(ALPHABET)
//                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
//                        .build();
//                em.persist(sticker);
//            }
//            for (int i = 0; i < 3; i++) {
//                Sticker sticker = Sticker.builder()
//                        .bookId(1L)
//                        .category(BIRTHDAY)
//                        .stickerImage("https://dzfv99wxq6tx0.cloudfront.net/stickers/birth_1.svg")
//                        .build();
//                em.persist(sticker);
//            }

//            PostedSticker postedSticker = PostedSticker.builder()
//                    .member(member1)
//                    .book(book1)
//                    .sticker(sticker1)
//                    .positionX(30)
//                    .positionY(60)
//                    .build();
//            em.persist(postedSticker);
        }

        private boolean isDatabaseEmpty() {
            Long count = em.createQuery("SELECT COUNT(m) FROM Member m", Long.class)
                    .getSingleResult();
            return count == 0;
        }
    }
}