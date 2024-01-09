package org.sopt.lequuServer.domain.note.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
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

    private int backgroundColor;

    @Column(nullable = false)
    private String backgroundImage;

    private int textColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public Note(String content, int backgroundColor, String backgroundImage, int textColor, Member member, Book book) {
        this.content = content;
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
        this.textColor = textColor;
        this.member = member;
        this.book = book;
    }

    public static Note of(String content, int backgroundColor, String backgroundImage, int textColor, Member member, Book book) {
        return new Note(content, backgroundColor, backgroundImage, textColor, member, book);
    }
}