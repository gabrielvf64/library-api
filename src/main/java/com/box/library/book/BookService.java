package com.box.library.book;

import com.box.library.author.Author;
import com.box.library.author.AuthorService;
import com.box.library.exception.BookNotFoundException;
import com.box.library.exception.NoFilterProvidedException;
import com.box.library.request.CreateBookRequest;
import com.box.library.request.UpdateBookRequest;
import com.box.library.response.CreateBookResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;
    private final AuthorService authorService;

    public BookService(BookRepository repository, AuthorService authorService) {
        this.repository = repository;
        this.authorService = authorService;
    }

    public CreateBookResponse create(CreateBookRequest request) {
        var authors = authorService.findAllByIds(request.authorsIds());
        var book = new Book(request.title(), authors, request.publisher(), request.isbn());
        repository.save(book);
        return bookToResponse(book, authors);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public List<Book> findAllByIds(List<Long> booksIds) {
        return repository.findAllById(booksIds);
    }

    public List<Book> findAllAvailableByIds(List<Long> bookIds) {
        return repository.findByIdInAndStatus(bookIds, BookStatus.AVAILABLE);
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

    public Book update(Long id, UpdateBookRequest request) {
        var existingBook = findById(id);
        var authors = authorService.findAllByIds(request.authorsIds());

        existingBook.setTitle(request.title());
        existingBook.setAuthors(authors);
        existingBook.setISBN(request.isbn());
        existingBook.setPublisher(request.publisher());
        existingBook.setStatus(request.status());

        return repository.save(existingBook);
    }

    public List<Book> findAllByFilter(String author, String title, String isbn, String publisher) {
        if (hasNoFilterAttribute(author, title, isbn, publisher)) {
            throw new NoFilterProvidedException();
        }
        return repository.findByAuthorsNameContainingIgnoreCaseOrTitleContainingIgnoreCaseOrISBNContainingIgnoreCaseOrPublisherContainingIgnoreCase(
                author, title, isbn, publisher);
    }

    private boolean hasNoFilterAttribute(String author, String title, String isbn, String publisher) {
        return !StringUtils.hasText(author) && !StringUtils.hasText(title)
                && !StringUtils.hasText(isbn) && !StringUtils.hasText(publisher);
    }

    private CreateBookResponse bookToResponse(Book book, List<Author> authors){
        return new CreateBookResponse(book.getId(), book.getTitle(), authors.stream().map(Author::getName).toList(), book.getPublisher(), book.getISBN(), book.getStatus().name());
    }

}
