package org.sopt.lequuServer.domain.favorite.repository;

import org.sopt.lequuServer.domain.book.model.Book;
import org.sopt.lequuServer.domain.favorite.model.Favorite;
import org.sopt.lequuServer.domain.member.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByMemberOrderByCreatedAtDesc(Member member);

    Optional<Favorite> findByMemberAndBook(Member member, Book book);

    List<Favorite> findByMember(Member member);

    @EntityGraph(attributePaths = {"book"})
    @Query("select f from Favorite f where f.member.id = :memberId")
    List<Favorite> findByMemberId(@Param("memberId") Long memberId);
}

