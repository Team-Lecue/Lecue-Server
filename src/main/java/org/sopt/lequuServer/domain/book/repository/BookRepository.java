

package org.sopt.lequuServer.domain.book.repository;

import org.sopt.lequuServer.domain.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}