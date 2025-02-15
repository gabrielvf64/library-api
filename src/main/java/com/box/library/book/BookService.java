package com.box.library.book;

import com.box.library.exception.BookNotFoundException;
import com.box.library.exception.NoFilterProvidedException;
import com.box.library.request.UpdateBook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Book update(Long id, UpdateBook request) {
        var existingBook = findById(id);

        existingBook.setTitle(request.title());
        existingBook.setAuthor(request.author());
        existingBook.setISBN(request.ISBN());
        existingBook.setPublisher(request.publisher());
        existingBook.setStatus(request.status());

        return repository.save(existingBook);
    }

    public List<Book> findAllByFilter(String author, String title, String isbn, String publisher) {
        if (hasNoFilterAttribute(author, title, isbn, publisher)) {
            throw new NoFilterProvidedException();
        }
        return repository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseOrISBNContainingIgnoreCaseOrPublisherContainingIgnoreCase(author, title, isbn, publisher);
    }

    private boolean hasNoFilterAttribute(String author, String title, String isbn, String publisher) {
        return !StringUtils.hasText(author) && !StringUtils.hasText(title)
                && !StringUtils.hasText(isbn) && !StringUtils.hasText(publisher);
    }

}
