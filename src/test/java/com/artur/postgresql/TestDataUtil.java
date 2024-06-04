package com.artur.postgresql;

import com.artur.postgresql.domain.Author;
import com.artur.postgresql.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Jonas Simpson")
                .age(22)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Lauren Steward")
                .age(37)
                .build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder()
                .isbn("9780132350884")
                .title("Clean Code")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("0134494164")
                .title("Clean Architecture")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("8575228374")
                .title("O Cientista da Computação Autodidata")
                .author(author)
                .build();
    }
}
