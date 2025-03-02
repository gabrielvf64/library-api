package com.box.library.author;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> findAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }
}
