package com.box.library.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    void testCreateBook() {
        // Arrange
        Book book = new Book(null, "Java Spring", "John Doe", "SpringPublisher", "1234567890");
        Book savedBook = new Book(1L, "Java Spring", "John Doe", "SpringPublisher", "1234567890");
        when(repository.save(book)).thenReturn(savedBook);

        // Act
        Book result = service.create(book);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Java Spring", result.getTitle());
        assertEquals("John Doe", result.getAuthor());
        assertEquals("SpringPublisher", result.getPublisher());
        assertEquals("1234567890", result.getISBN());
        assertEquals(BookStatus.AVAILABLE, result.getStatus());
    }

    @Test
    void testFindAllPageable() {
        // Arrange
        Book book1 = new Book(1L, "Refactoring", "Martin Fowler", "Addison-Wesley", "978-0201485677");
        Book book2 = new Book(2L, "Clean Code", "Robert C. Martin", "Prentice Hall", "978-0132350884");
        List<Book> books = List.of(book1, book2);
        Page<Book> booksPage = new PageImpl<>(books);
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findAll(any(Pageable.class))).thenReturn(booksPage);

        // Act
        Page<Book> result = service.findAll(pageable);

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals(books, result.getContent());
    }
}