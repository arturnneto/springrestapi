package com.artur.booksapiapplication.service.impl;

import com.artur.booksapiapplication.domain.entities.BookEntity;
import com.artur.booksapiapplication.repositories.BookRepository;
import com.artur.booksapiapplication.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExistent(String isbn) {
        return bookRepository.existsById(isbn);
    }
}
