package com.artur.booksapiapplication.service.impl;

import com.artur.booksapiapplication.domain.entities.BookEntity;
import com.artur.booksapiapplication.repositories.BookRepository;
import com.artur.booksapiapplication.service.BookService;
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
