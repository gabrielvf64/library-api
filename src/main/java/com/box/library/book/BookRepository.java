package com.box.library.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b " +
            "JOIN b.authors a " +
            "WHERE (:author IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:isbn IS NULL OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :isbn, '%'))) " +
            "AND (:publisher IS NULL OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :publisher, '%')))")
    List<Book> findAllByFilter(@Param("author") String author,
                               @Param("title") String title,
                               @Param("isbn") String isbn,
                               @Param("publisher") String publisher);

    @EntityGraph(attributePaths = "authors")
    @NonNull
    List<Book> findAll();

    @EntityGraph(attributePaths = "authors")
    @NonNull
    Page<Book> findAll(Pageable pageable);

    List<Book> findByIdInAndStatus(List<Long> ids, BookStatus status);
}
