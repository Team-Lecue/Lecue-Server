package org.sopt.lequuServer.global.s3.test;

import org.sopt.lequuServer.domain.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestJpaRepository extends JpaRepository<Book, Long> {

}