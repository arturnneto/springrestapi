package com.artur.booksapiapplication.service;

import com.artur.booksapiapplication.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity save(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExistent(String isbn);
}
