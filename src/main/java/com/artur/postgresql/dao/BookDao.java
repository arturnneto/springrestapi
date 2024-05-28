package com.artur.postgresql.dao;

import com.artur.postgresql.domain.Book;

import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);
}
