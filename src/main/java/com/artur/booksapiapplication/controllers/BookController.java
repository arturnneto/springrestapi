package com.artur.booksapiapplication.controllers;

import com.artur.booksapiapplication.domain.dto.BookDto;
import com.artur.booksapiapplication.domain.entities.BookEntity;
import com.artur.booksapiapplication.mappers.Mapper;
import com.artur.booksapiapplication.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;

    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        List<BookEntity> bookList = bookService.findAll();
        return bookList.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }
}
