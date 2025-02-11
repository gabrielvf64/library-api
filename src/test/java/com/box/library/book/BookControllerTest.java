package com.box.library.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testFindAllUsingTitleFilter() throws Exception {
        List<Book> books = BookFactory.createBooks();
        when(service.findAllByFilter(anyString(), anyString(), anyString())).thenReturn(books.subList(0, 2));

        mockMvc.perform(get("/api/v1/books/filter")
                        .param("title", "A Song of Ice and Fire")
                        .param("author", "")
                        .param("isbn", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A Song of Ice and Fire"))
                .andExpect(jsonPath("$[0].author").value("George R. R. Martin"))
                .andExpect(jsonPath("$[1].title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$[1].author").value("J. R. R. Tolkien"));
    }

    @Test
    public void testFindAllUsingAuthorFilter() throws Exception {
        List<Book> books = BookFactory.createBooks();
        when(service.findAllByFilter(anyString(), anyString(), anyString())).thenReturn(books.subList(1, 3));

        mockMvc.perform(get("/api/v1/books/filter")
                        .param("author", "J. R. R. Tolkien")
                        .param("title", "")
                        .param("isbn", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$[0].author").value("J. R. R. Tolkien"))
                .andExpect(jsonPath("$[1].title").value("The Hobbit"))
                .andExpect(jsonPath("$[1].author").value("J. R. R. Tolkien"));
    }

    @Test
    public void testFindAllUsingIsbnFilter() throws Exception {
        List<Book> books = BookFactory.createBooks();
        when(service.findAllByFilter(anyString(), anyString(), anyString())).thenReturn(books.subList(0, 2));

        mockMvc.perform(get("/api/v1/books/filter")
                        .param("isbn", "1234567890")
                        .param("title", "")
                        .param("author", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A Song of Ice and Fire"))
                .andExpect(jsonPath("$[0].author").value("George R. R. Martin"))
                .andExpect(jsonPath("$[1].title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$[1].author").value("J. R. R. Tolkien"));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = BookFactory.createBook();
        when(service.create(any(Book.class))).thenReturn(book);

        String bookJson = BookFactory.createBookJson(book.getTitle(), book.getAuthor());

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A Song of Ice and Fire"))
                .andExpect(jsonPath("$.author").value("George R. R. Martin"));
    }
}