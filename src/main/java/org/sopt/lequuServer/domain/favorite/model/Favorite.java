package org.sopt.lequuServer.domain.favorite.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.member.model.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "favorite")
public class Favorite {

    @Id
    @Column(name = "favorite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public Favorite(Member member, Book book) {
        this.member = member;
        this.book = book;
    }

    public static Favorite of(Member member, Book book) {
        Favorite favorite = new Favorite(member, book);
        book.addFavorite(favorite);
        member.addFavorite(favorite);
        return favorite;
    }
}