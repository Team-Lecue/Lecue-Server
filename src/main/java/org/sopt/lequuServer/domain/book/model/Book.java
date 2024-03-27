package org.sopt.lequuServer.domain.book.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.member.model.Member;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book")
public class Book extends BaseTimeEntity {

    @Id
    @Column(name = "book_id")
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

    private String backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "book")
    private final List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        notes.add(note);
    }

    @OneToMany(mappedBy = "book")
    private final List<PostedSticker> postedStickers = new ArrayList<>();

    public void addPostedSticker(PostedSticker postedSticker) {
        postedStickers.add(postedSticker);
    }

    @OneToMany(mappedBy = "book")
    private final List<Favorite> favorites = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }

    @Column(nullable = false)
    private int popularRate;

    @Builder
    public Book(String uuid, String favoriteName, String favoriteImage, String title, String description, String backgroundColor, Member member, int popularRate) {
        this.uuid = uuid;
        this.favoriteName = favoriteName;
        this.favoriteImage = favoriteImage;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.member = member;
        this.popularRate = popularRate;
    }

    public static Book of(String uuid, String favoriteName, String favoriteImage, String title, String description, String backgroundColor, Member member, int popularRate) {
        return new Book(uuid, favoriteName, favoriteImage, title, description, backgroundColor, member, popularRate);
    }
}
