package com.box.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(
            String author, String title, String isbn);
}
