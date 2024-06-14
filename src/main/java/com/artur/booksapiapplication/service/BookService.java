package com.artur.booksapiapplication.service;

import com.artur.booksapiapplication.domain.entities.BookEntity;

import java.util.List;

public interface BookService {

    BookEntity save(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
