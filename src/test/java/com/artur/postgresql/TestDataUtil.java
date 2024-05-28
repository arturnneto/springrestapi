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

    public static Book createTestBook() {
        return Book.builder()
                .isbn("9780132350884")
                .title("Clean Code")
                .authorId(1L)
                .build();
    }
}
