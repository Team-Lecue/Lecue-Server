package org.sopt.lequuServer;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.domain.member.model.SocialPlatform;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.sticker.model.Sticker;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.sopt.lequuServer.domain.sticker.model.StickerCategory.*;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    private static final List<String> CHARACTER_STICKERS = Arrays.asList(
            "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/04d691f2-eb9c-4556-8962-005c58ce61cf.jpg",
            "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/0215b8a5-d7a1-40c3-b291-5174b1747074.jpg",
            "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/c446705f-d96f-4cef-b490-62979fc52cd9.jpg",
            "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/1d8ac983-4862-4687-b27a-324a4ecb8ae6.jpg",
            "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/ea9a990a-e6e5-4789-9911-a967095d3cdc.jpg",
            "https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/38e4509e-61ba-42e7-a823-8b379c1be022.jpg"
    );

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Transactional
        public void dbInit() {
            Member member1 = Member.builder()
                    .socialPlatform(SocialPlatform.KAKAO)
                    .socialId("3251153440")
                    .build();
            member1.updateSocialInfo("레큐", "http://k.kakaocdn.net/dn/1G9kp/btsAot8liOn/8CWudi3uy07rvFNUkk3ER0/img_640x640.jpg");
            em.persist(member1);

            Book book1 = Book.builder()
                    .uuid("ee4f66f9-9cf4-4b28-90f4-f71d0ecba021")
                    .favoriteName("LeoJ")
                    .favoriteImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/books/favorite_image/b4006561-382b-479e-ae1d-e841922e883f.jpg")
                    .title("1번째 레큐북")
                    .description("레큐북의 내용입니다!")
                    .backgroundColor(1)
                    .member(member1)
                    .popularRate(0)
                    .build();
            em.persist(book1);
            for (int i = 1; i < 7; i++) {
                Book book = Book.builder()
                        .uuid("ee4f66f9-9cf4-4b28-90f4-f71d0ecba02" + String.valueOf(i + 1))
                        .favoriteName("LeoJ")
                        .favoriteImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/books/favorite_image/b4006561-382b-479e-ae1d-e841922e883f.jpg")
                        .title(String.valueOf(i + 1) + "번째 레큐북")
                        .description("레큐북의 내용입니다!")
                        .backgroundColor(1)
                        .member(member1)
                        .popularRate(i)
                        .build();
                em.persist(book);
            }

            for (int i = 0; i < 3; i++) {
                Note note = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라")
                        .background("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/notes/background_image/676c2ca3-f868-423f-8000-a0bcb67dc797.jpg")
                        .textColor(i)
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note);
            }
            for (int i = 0; i < 3; i++) {
                Note note = Note.builder()
                        .content("레큐노트 내용입니다 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라 블라블라블라")
                        .background(String.valueOf(i))
                        .textColor(i)
                        .member(member1)
                        .book(book1)
                        .build();
                em.persist(note);
            }

            Sticker sticker1 = Sticker.builder()
                    .bookId(0L)
                    .category(ALPHABET)
                    .stickerImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
                    .build();
            em.persist(sticker1);
            for (int i = 0; i < 3; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(ALPHABET)
                        .stickerImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
                        .build();
                em.persist(sticker);
            }
            for (int i = 0; i < 6; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(BIRTHDAY)
                        .stickerImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
                        .build();
                em.persist(sticker);
            }
            for (String characterSticker : CHARACTER_STICKERS) {
                Sticker sticker = Sticker.builder()
                        .bookId(0L)
                        .category(CHARACTER)
                        .stickerImage(characterSticker)
                        .build();
                em.persist(sticker);
            }

            for (int i = 0; i < 2; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(1L)
                        .category(ALPHABET)
                        .stickerImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
                        .build();
                em.persist(sticker);
            }
            for (int i = 0; i < 3; i++) {
                Sticker sticker = Sticker.builder()
                        .bookId(1L)
                        .category(BIRTHDAY)
                        .stickerImage("https://lequu-server-bucket.s3.ap-northeast-2.amazonaws.com/stickers/8d83b1c1-1e2c-437b-a2f5-e3ce96ce6d35.jpg")
                        .build();
                em.persist(sticker);
            }

            PostedSticker postedSticker = PostedSticker.builder()
                    .member(member1)
                    .book(book1)
                    .sticker(sticker1)
                    .positionX(30)
                    .positionY(60)
                    .build();
            em.persist(postedSticker);
        }
    }
}