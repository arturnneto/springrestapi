package com.artur.postgresql.service.impl;

import com.artur.postgresql.domain.entities.BookEntity;
import com.artur.postgresql.repositories.BookRepository;
import com.artur.postgresql.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
