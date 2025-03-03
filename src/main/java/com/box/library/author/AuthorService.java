package com.box.library.author;

import com.box.library.book.BookService;
import com.box.library.exception.AuthorNotFoundException;
import com.box.library.request.CreateAuthorRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final BookService bookService;

    public AuthorService(AuthorRepository repository, @Lazy BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
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
        var author = new Author(request.name(), books);
        books.forEach(book -> book.getAuthors().add(author));
        return repository.save(author);
    }

}
