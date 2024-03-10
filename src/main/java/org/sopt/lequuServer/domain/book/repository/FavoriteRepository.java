package org.sopt.lequuServer.domain.book.repository;

import org.sopt.lequuServer.domain.book.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}

