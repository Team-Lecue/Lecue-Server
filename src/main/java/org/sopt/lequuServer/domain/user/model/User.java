package org.sopt.lequuServer.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.lequuServer.domain.postit.model.Postit;
import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<RollingPaper> rollingPapers = new ArrayList<>();

    public void addRollingPaper(RollingPaper rollingPaper) {
        rollingPapers.add(rollingPaper);
    }

    @OneToMany(mappedBy = "user")
    private List<Postit> postits = new ArrayList<>();

    public void addPostit(Postit postit) {
        postits.add(postit);
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public static User of(String nickname) {
        return new User(nickname);
    }
}