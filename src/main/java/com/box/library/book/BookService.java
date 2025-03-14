package com.box.library.book;

import com.box.library.author.Author;
import com.box.library.author.AuthorService;
import com.box.library.exception.BookNotFoundException;
import com.box.library.exception.NoFilterProvidedException;
import com.box.library.request.CreateBookRequest;
import com.box.library.request.UpdateBookRequest;
import com.box.library.response.AuthorResponse;
import com.box.library.response.BookResponse;
import com.box.library.response.GenericPagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<BookResponse> findAll() {
        return toBookListResponse(repository.findAll());
    }

    public GenericPagedResponse<BookResponse> findAllPageable(Pageable pageable) {
        Page<Book> entityPage = repository.findAll(pageable);
        Page<BookResponse> responsePage = entityPage.map(this::toBookResponse);
        return GenericPagedResponse.fromPage(responsePage);
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
        existingBook.setIsbn(request.isbn());
        existingBook.setPublisher(request.publisher());
        existingBook.setStatus(request.status());

        return repository.save(existingBook);
    }

    public List<BookResponse> findAllByFilter(String author, String title, String isbn, String publisher) {
        if (hasNoFilterAttribute(author, title, isbn, publisher)) {
            throw new NoFilterProvidedException();
        }
        List<Book> filteredBooks = repository.findAllByFilter(author, title, isbn, publisher);
        return toBookListResponse(filteredBooks);
    }

    private boolean hasNoFilterAttribute(String author, String title, String isbn, String publisher) {
        return !StringUtils.hasText(author) && !StringUtils.hasText(title)
                && !StringUtils.hasText(isbn) && !StringUtils.hasText(publisher);
    }

    private List<BookResponse> toBookListResponse(List<Book> books) {
        return books.stream()
                .map(this::toBookResponse)
                .toList();
    }

    private BookResponse toBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                getAuthorResponse(book),
                book.getPublisher(),
                book.getIsbn(),
                book.getStatus().name()
        );
    }

    private static List<AuthorResponse> getAuthorResponse(Book book) {
        return book.getAuthors()
                .stream()
                .map(author -> new AuthorResponse(author.getId(), author.getName()))
                .toList();
    }
    private CreateBookResponse bookToResponse(Book book, List<Author> authors){
        return new CreateBookResponse(book.getId(), book.getTitle(), authors.stream().map(Author::getName).toList(), book.getPublisher(), book.getISBN(), book.getStatus().name());
    }

}
