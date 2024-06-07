package com.artur.postgresql.service;

import com.artur.postgresql.domain.entities.BookEntity;

public interface BookService {

    BookEntity save(String isbn, BookEntity book);
}
