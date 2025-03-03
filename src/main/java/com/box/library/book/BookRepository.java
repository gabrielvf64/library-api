package com.box.library.book;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Modifying
    @Query(value = "DELETE FROM book_author ba WHERE ba.author_id = :authorId", nativeQuery = true)
    void deleteAuthorFromBooks(Long authorId);
}
