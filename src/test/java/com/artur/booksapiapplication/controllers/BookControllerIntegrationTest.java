package com.artur.booksapiapplication.controllers;

import com.artur.booksapiapplication.TestDataUtil;
import com.artur.booksapiapplication.domain.dto.BookDto;
import com.artur.booksapiapplication.domain.entities.BookEntity;
import com.artur.booksapiapplication.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(BookService bookService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String BookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        BookEntity savedBookEntity = bookService.save(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDto(null);
        testBookDto.setIsbn(savedBookEntity.getIsbn());
        String BookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String BookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        BookEntity savedBookEntity = bookService.save(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDto(null);
        testBookDto.setIsbn(savedBookEntity.getIsbn());
        testBookDto.setTitle("UPDATED");
        String BookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        );
    }

    @Test
    public void testThatListAllReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBook(null);
        bookService.save(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook.getTitle())
        );
    }

    @Test
    public void testThatGetOneBookSuccessfullyReturnsHttp200WhenBookIsPresent() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBook(null);
        bookService.save(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetOneBookSuccessfullyReturnsHttp404WhenBookIsNotPresent() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBook(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetOneBookSuccessfullyReturnsBookWhenABookIsPresent() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBook(null);
        bookService.save(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook.getTitle()) // Expected: Abigail Rose
        );
    }

}
