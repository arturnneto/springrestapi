package com.artur.booksapiapplication.controllers;

import com.artur.booksapiapplication.TestDataUtil;
import com.artur.booksapiapplication.domain.dto.AuthorDto;
import com.artur.booksapiapplication.domain.entities.AuthorEntity;
import com.artur.booksapiapplication.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.authorService = authorService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName()) // Expected: Abigail Rose
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge()) // Expected: 80
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testAuthor.getName()) // Expected: Abigail Rose
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(testAuthor.getAge()) // Expected: 80
        );
    }

    @Test
    public void testThatGetOneAuthorSuccessfullyReturnsHttp200WhenAuthorIsPresent() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetOneAuthorSuccessfullyReturnsHttp404WhenAuthorIsNotPresent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetOneAuthorSuccessfullyReturnsAuthorWhenAuthorIsPresent() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(testAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName()) // Expected: Abigail Rose
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge()) // Expected: 80
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDto();
        String authorJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp404WhenAuthorDoesNotExists() throws Exception {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDto();
        String testAuthorJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);

        AuthorEntity testAuthorDto = TestDataUtil.createTestAuthorB();
        testAuthorDto.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHttp200WhenOk() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDto();
        testAuthorDto.setName("UPDATED");
        String authorJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHttp404WhenAuthorIsNotFound() throws Exception {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDto();
        testAuthorDto.setName("UPDATED");
        String authorJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);

        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDto();
        testAuthorDto.setName(testAuthorEntityB.getName());
        String authorJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorEntityB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/99999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }


}
