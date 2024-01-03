package org.sopt.lequuServer.domain.book.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.domain.user.model.User;
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

    private int backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    @Builder
    public Book(String uuid, String favoriteName, String favoriteImage, String title, String description, int backgroundColor, User user) {
        this.uuid = uuid;
        this.favoriteName = favoriteName;
        this.favoriteImage = favoriteImage;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.user = user;
    }

    public static Book of(String uuid, String favoriteName, String favoriteImage, String title, String description, int backgroundColor, User user) {
        return new Book(uuid, favoriteName, favoriteImage, title, description, backgroundColor, user);
    }

    // TODO S3 테스트용, 추후 삭제
    public Book(String uuid, String favoriteName, String favoriteImage, String title, String description, int backgroundColor) {
        this.uuid = uuid;
        this.favoriteName = favoriteName;
        this.favoriteImage = favoriteImage;
        this.title = title;
        this.description = description;
        this.backgroundColor = backgroundColor;
    }

    // TODO S3 테스트용, 추후 삭제
    public static Book test(String favoriteImage, String title) {
        return new Book("test", "test", favoriteImage, title, "test", 1);
    }
}
