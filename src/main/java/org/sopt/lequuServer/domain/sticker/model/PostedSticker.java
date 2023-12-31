package org.sopt.lequuServer.domain.sticker.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posted_sticker")
public class PostedSticker extends BaseTimeEntity {

    @Id
    @Column(name = "posted_sticker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int positionX;

    private int positionY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticker_id")
    private Sticker sticker;

    @Builder
    public PostedSticker(int positionX, int positionY, Member member, Book book, Sticker sticker) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.member = member;
        this.book = book;
        this.sticker = sticker;
    }

    public static PostedSticker of(int positionX, int positionY, Member member, Book book, Sticker sticker) {
        return new PostedSticker(positionX, positionY, member, book, sticker);
    }
}