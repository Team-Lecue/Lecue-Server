package org.sopt.lequuServer.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.note.model.Note;
import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.sticker.model.PostedSticker;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

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
    @OneToMany(mappedBy = "user")
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @OneToMany(mappedBy = "user")
    private final List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        notes.add(note);
    }

    @OneToMany(mappedBy = "user")
    private final List<PostedSticker> postedStickers = new ArrayList<>();

    public void addPostedSticker(PostedSticker postedSticker) {
        postedStickers.add(postedSticker);
    }
  
    /**
     * 유저가 최초로 생성될 때 필요한 최소 정보
     */
    @Builder
    public User(SocialPlatform socialPlatform, String socialId) {
        this.socialPlatform = socialPlatform;
        this.socialId = socialId;
    }

    public static User of(SocialPlatform socialPlatform, String socialId) {
        return new User(socialPlatform, socialId);
    }
}