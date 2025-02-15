package com.box.library.book;

import com.box.library.exception.BookNotFoundException;
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

    public Book findById(Long bookId) {
        return repository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public void deleteById(Long bookId) {
        if (!repository.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        repository.deleteById(bookId);
    }

    public Book update(Long bookId, Book updateBook) {
        var existingBook = findById(bookId);

        existingBook.setTitle(updateBook.getTitle());
        existingBook.setAuthor(updateBook.getAuthor());
        existingBook.setISBN(updateBook.getISBN());
        existingBook.setPublisher(updateBook.getPublisher());
        existingBook.setStatus(updateBook.getStatus());

        return repository.save(existingBook);
    }

}
