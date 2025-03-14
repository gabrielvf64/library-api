package com.box.library.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorsNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrISBNContainingIgnoreCaseOrPublisherContainingIgnoreCase(
            String author, String title, String isbn, String publisher);

    @EntityGraph(attributePaths = "authors")
    @NonNull
    List<Book> findAll();

    @EntityGraph(attributePaths = "authors")
    @NonNull
    Page<Book> findAll(Pageable pageable);

    List<Book> findByIdInAndStatus(List<Long> ids, BookStatus status);
}
