package org.sopt.lequuServer.domain.favorite.repository;

import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}

