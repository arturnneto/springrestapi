package com.artur.booksapiapplication.service;

import com.artur.booksapiapplication.domain.entities.BookEntity;

public interface BookService {

    BookEntity save(String isbn, BookEntity book);
}
