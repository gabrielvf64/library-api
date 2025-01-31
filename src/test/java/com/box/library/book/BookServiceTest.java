package com.box.library.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testCreateBook() {
        // Arrange
        Book book = new Book(null, "Java Spring", "John Doe", "SpringPublisher", "1234567890");
        Book savedBook = new Book(1L, "Java Spring", "John Doe", "SpringPublisher", "1234567890");
        when(bookRepository.save(book)).thenReturn(savedBook);

        // Act
        Book result = bookService.create(book);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Java Spring", result.getTitle());
        assertEquals("John Doe", result.getAuthor());
        assertEquals("SpringPublisher", result.getPublisher());
        assertEquals("1234567890", result.getISBN());
        assertEquals(BookStatus.AVAILABLE, result.getStatus());
    }

}