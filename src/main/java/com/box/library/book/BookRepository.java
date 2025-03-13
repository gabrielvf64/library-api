package com.box.library.book;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorsNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrPublisherContainingIgnoreCase(
            String author, String title, String isbn, String publisher);

    @EntityGraph(attributePaths = "authors")
    @NonNull
    List<Book> findAll();

    List<Book> findByIdInAndStatus(List<Long> ids, BookStatus status);
}
