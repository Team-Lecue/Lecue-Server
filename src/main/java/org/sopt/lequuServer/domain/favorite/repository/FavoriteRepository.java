package org.sopt.lequuServer.domain.favorite.repository;

import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByMemberOrderByCreatedAtDesc(Member member);
}

