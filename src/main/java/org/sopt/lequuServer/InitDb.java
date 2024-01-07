package org.sopt.lequuServer;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
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

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        @Transactional
        public void dbInit() {
            Member member = Member.builder()
                    .socialPlatform(SocialPlatform.KAKAO)
                    .socialId("dwq4d1sa68xx1qw61")
                    .build();
            em.persist(member);

            for (int i = 0; i < 7; i++) {
                Book book = Book.builder()
                        .uuid("dwq65d19asx6qw1c")
                        .favoriteName(String.valueOf(i))
                        .favoriteImage("dqw84dsaac9q9")
                        .title(String.valueOf(i))
                        .description("test")
                        .backgroundColor(11)
                        .member(member)
                        .isPopular(true)
                        .build();
                em.persist(book);
            }

            Book book1 = Book.builder()
                    .uuid("dwq65d19asx6qw1c")
                    .favoriteName("test")
                    .favoriteImage("dqw84dsaac9q9")
                    .title("test")
                    .description("test")
                    .backgroundColor(11)
                    .member(member)
                    .isPopular(true)
                    .build();
            em.persist(book1);

            for (int i = 0; i < 7; i++) {
                Note note = Note.builder()
                        .content(String.valueOf(i))
                        .background("back")
                        .textColor(i)
                        .member(member)
                        .book(book1)
                        .build();
                em.persist(note);
            }
        }
    }
}
