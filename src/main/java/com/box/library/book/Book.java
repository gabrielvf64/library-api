package com.box.library.book;

import com.box.library.author.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnoreProperties("books")
    private List<Author> authors;

    private String publisher;
    private String ISBN;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;

    public Book(String title, List<Author> authors, String publisher, String ISBN) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.ISBN = ISBN;
    }

}
