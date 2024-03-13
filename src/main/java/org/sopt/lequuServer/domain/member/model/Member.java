package org.sopt.lequuServer.domain.member.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 소셜 로그인 관련
     */
    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;

    @Column(nullable = false)
    private String socialId;

    /**
     * 카카오 로그인 한정 제공 정보
     */
    private String socialNickname;

    private String socialProfileImage;

    // 로그인 새롭게 할 때마다 해당 필드들 업데이트
    public void updateSocialInfo(String socialNickname, String socialProfileImage) {
        this.socialNickname = socialNickname;
        this.socialProfileImage = socialProfileImage;
    }

    /**
     * 연관 되어 있는 엔티티
     */
    @OneToMany(mappedBy = "member")
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @OneToMany(mappedBy = "member")
    private final List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        notes.add(note);
    }

    @OneToMany(mappedBy = "member")
    private final List<PostedSticker> postedStickers = new ArrayList<>();

    public void addPostedSticker(PostedSticker postedSticker) {
        postedStickers.add(postedSticker);
    }

    @OneToMany(mappedBy = "member")
    private final List<Favorite> favorites = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }

    /**
     * 유저가 최초로 생성될 때 필요한 최소 정보
     */
    @Builder
    public Member(SocialPlatform socialPlatform, String socialId) {
        this.socialPlatform = socialPlatform;
        this.socialId = socialId;
    }

    public static Member of(SocialPlatform socialPlatform, String socialId) {
        return new Member(socialPlatform, socialId);
    }
}