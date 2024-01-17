package org.sopt.lequuServer.domain.log.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.lequuServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bad_word_log")
public class BadWordLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bad_word_log_id")
    private Long id;

    private Long memberId;

    private String originText;

    @Builder
    public BadWordLog(Long memberId, String originText) {
        this.memberId = memberId;
        this.originText = originText;
    }

    public static BadWordLog of(Long memberId, String originText) {
        return new BadWordLog(memberId, originText);
    }
}
