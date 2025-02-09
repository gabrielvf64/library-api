package com.box.library.user;

import com.box.library.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryUserTest {

    private LibraryUser user;
    private Book book1, book2, book3, book4;

    @BeforeEach
    void setUp() {
        user = new LibraryUser("Eduardo Rodrigues", "12345678901");

        book1 = new Book(1L, "Livro 1", "Autor 1", "Editora 1", "ISBN1");
        book2 = new Book(2L, "Livro 2", "Autor 2", "Editora 2", "ISBN2");
        book3 = new Book(3L, "Livro 3", "Autor 3", "Editora 3", "ISBN3");
        book4 = new Book(4L, "Livro 4", "Autor 4", "Editora 4", "ISBN4");
    }

    @Test
    void testCriacaoUsuario() {
        assertEquals("Eduardo Rodrigues", user.getName());
        assertEquals("12345678901", user.getCpf());
        assertTrue(user.getBorrowedBooks().isEmpty());
    }

    @Test
    void testEmprestarLivroComSucesso() {
        assertTrue(user.borrowBook(book1));
        assertTrue(user.borrowBook(book2));
        assertTrue(user.borrowBook(book3));

        assertEquals(3, user.getBorrowedBooks().size());
    }

    @Test
    void testNaoPodeEmprestarMaisDeTresLivros() {
        user.borrowBook(book1);
        user.borrowBook(book2);
        user.borrowBook(book3);

        assertFalse(user.borrowBook(book4));
        assertEquals(3, user.getBorrowedBooks().size());
    }

    @Test
    void testDevolverLivroComSucesso() {
        user.borrowBook(book1);
        user.borrowBook(book2);

        assertTrue(user.returnBook(book1));
        assertEquals(1, user.getBorrowedBooks().size());
    }

    @Test
    void testNaoPodeDevolverLivroNaoEmprestado() {
        assertFalse(user.returnBook(book1));
    }
}
