package com.artur.booksapiapplication;

import com.artur.booksapiapplication.domain.dto.AuthorDto;
import com.artur.booksapiapplication.domain.dto.BookDto;
import com.artur.booksapiapplication.domain.entities.AuthorEntity;
import com.artur.booksapiapplication.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Jonas Simpson")
                .age(28)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Lauren Steward")
                .age(37)
                .build();
    }

    public static AuthorDto createTestAuthorDto() {
        return AuthorDto.builder()
                .id(4L)
                .name("Artur Jorge")
                .age(22)
                .build();
    }

    public static BookEntity createTestBook(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("9780132350884")
                .title("Clean Code")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("0134494164")
                .title("Clean Architecture")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("8575228374")
                .title("O Cientista da Computação Autodidata")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDto(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("9780132350884")
                .title("Clean Code")
                .author(authorDto)
                .build();
    }
}
