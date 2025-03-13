package com.box.library.author;

import com.box.library.book.Book;
import com.box.library.book.BookService;
import com.box.library.exception.AuthorNotFoundException;
import com.box.library.exception.BookNotFoundException;
import com.box.library.request.CreateAuthorRequest;
import com.box.library.request.UpdateAuthorRequest;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final BookService bookService;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository repository, @Lazy BookService bookService, AuthorMapper authorMapper) {
        this.repository = repository;
        this.bookService = bookService;
        this.authorMapper = authorMapper;
    }

    public List<Author> findAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }

    public Author findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author create(CreateAuthorRequest request) {
        var books = bookService.findAllByIds(request.booksIds());
        var author = authorMapper.toAuthor(request, books);
        books.forEach(book -> book.getAuthors().add(author));
        return repository.save(author);
    }

    public Author update(Long id, UpdateAuthorRequest request) {
        var author = findById(id);
        var books = validateAndGetBooksByIds(request.booksIds());

        removeOldBooksAssociations(author, books);
        addNewBooksAssociations(author, books);

        var updatedAuthor = authorMapper.toAuthor(request, new ArrayList<>(books), id);
        return repository.save(updatedAuthor);
    }

    @Transactional
    public void deleteById(Long id) {
        var author = findById(id);
        removeAllBooksAssociations(author);
        repository.deleteById(id);
    }

    private Set<Book> validateAndGetBooksByIds(List<Long> booksIds) {
        List<Book> books = bookService.findAllByIds(booksIds);

        Set<Long> foundIds = books.stream()
                .map(Book::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = booksIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new BookNotFoundException(missingIds.stream().toList());
        }

        return new HashSet<>(books);
    }

    private void removeOldBooksAssociations(Author author, Set<Book> books) {
        author.getBooks().stream()
                .filter(book -> !books.contains(book))
                .forEach(book -> book.getAuthors().remove(author));
    }

    private void removeAllBooksAssociations(Author author) {
        author.getBooks().forEach(book -> book.getAuthors().remove(author));
    }

    private void addNewBooksAssociations(Author author, Set<Book> books) {
        books.stream()
                .filter(book -> !book.getAuthors().contains(author))
                .forEach(book -> book.getAuthors().add(author));
    }

}
