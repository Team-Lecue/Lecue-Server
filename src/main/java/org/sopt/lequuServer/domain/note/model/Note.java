package org.sopt.lequuServer.domain.note.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.user.model.User;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "note")
public class Note extends BaseTimeEntity {

    @Id
    @Column(name = "note_id")
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
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public Note(String content, String background, int textColor, User user, Book book) {
        this.content = content;
        this.background = background;
        this.textColor = textColor;
        this.user = user;
        this.book = book;
    }

    public static Note of(String content, String background, int textColor, User user, Book book) {
        return new Note(content, background, textColor, user, book);
    }
}