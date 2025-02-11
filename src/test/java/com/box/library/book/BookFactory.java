package com.box.library.book;

import java.util.Arrays;
import java.util.List;

public class BookFactory {

    public static List<Book> createBooks() {
        Book book1 = new Book();
        book1.setTitle("A Song of Ice and Fire");
        book1.setAuthor("George R. R. Martin");
        book1.setPublisher("Publisher1");
        book1.setIsbn("1234567890");

        Book book2 = new Book();
        book2.setTitle("The Lord of the Rings");
        book2.setAuthor("J. R. R. Tolkien");
        book2.setPublisher("Publisher2");
        book2.setIsbn("0987654321");

        Book book3 = new Book();
        book3.setTitle("The Hobbit");
        book3.setAuthor("J. R. R. Tolkien");
        book3.setPublisher("Publisher3");
        book3.setIsbn("1122334455");

        return Arrays.asList(book1, book2, book3);
    }

    public static Book createBook() {
        Book book = new Book();
        book.setTitle("A Song of Ice and Fire");
        book.setAuthor("George R. R. Martin");
        book.setPublisher("Publisher1");
        book.setIsbn("1234567890");

        return book;
    }

    public static String createBookJson(String title, String author) {
        return """
                {
                    "title": "%s",
                    "author": "%s"
                }
                """.formatted(title, author);
    }
}
