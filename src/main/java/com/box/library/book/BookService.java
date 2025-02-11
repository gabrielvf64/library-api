package com.box.library.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public List<Book> findAllByFilter(String author, String title, String isbn) {
        if (hasNoFilterAttribute(author, title, isbn)) {
            throw new IllegalArgumentException("Pelo menos um parâmetro de busca deve ser informado.");
        }
        return repository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseOrIsbnContainingIgnoreCase(author,
                title, isbn);
    }

    private boolean hasNoFilterAttribute(String author, String title, String isbn) {
        return isAttributeNullOrBlank(author) && isAttributeNullOrBlank(title)
                && isAttributeNullOrBlank(isbn);
    }

    private boolean isAttributeNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}
