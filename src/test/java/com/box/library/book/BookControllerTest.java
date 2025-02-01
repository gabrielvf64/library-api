package com.box.library.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = createBook("Refactoring", "Martin Fowler");
        when(service.create(any(Book.class))).thenReturn(book);

        String bookJson = createBookJson("Refactoring", "Martin Fowler");


        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andExpect(jsonPath("$.author").value("Martin Fowler"));
    }

    @Test
    public void testfindAllPageable() throws Exception {
        Book book1 = createBook("Refactoring", "Martin Fowler");
        Book book2 = createBook("Clean Code", "Robert C. Martin");
        List<Book> books = List.of(book1, book2);
        Page<Book> booksPage = new PageImpl<>(books);

        when(service.findAll(any(Pageable.class))).thenReturn(booksPage);
        when(service.count()).thenReturn((long) books.size());

        mockMvc.perform(get("/api/v1/books")
                        .header("Range", "items=0-9")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Refactoring"))
                .andExpect(jsonPath("$[0].author").value("Martin Fowler"))
                .andExpect(jsonPath("$[1].title").value("Clean Code"))
                .andExpect(jsonPath("$[1].author").value("Robert C. Martin"))
                .andExpect(header().string("Content-Range", "books 0-2/2"));
    }


    private Book createBook(String title, String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        return book;
    }

    private String createBookJson(String title, String author) {
        return """
                {
                    "title": "%s",
                    "author": "%s"
                }
                """.formatted(title, author);
    }
}