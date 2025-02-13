package com.box.library.book;

import jakarta.persistence.*;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private String ISBN;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;

    public Book() {
    }

    public Book(Long id, String title, String author, String publisher, String ISBN) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.status = BookStatus.AVAILABLE;
    }

    public void updateFields(Book updatedBook){
        if (StringUtils.hasText(updatedBook.getAuthor())){
            this.setAuthor(updatedBook.getAuthor());
        }
        if (StringUtils.hasText(updatedBook.getPublisher())){
            this.setPublisher(updatedBook.getPublisher());
        }
        if (StringUtils.hasText(updatedBook.getISBN())){
            this.setISBN(updatedBook.getISBN());
        }
        if (StringUtils.hasText(updatedBook.getTitle())){
            this.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getStatus() != null){
            this.setStatus(updatedBook.getStatus());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
