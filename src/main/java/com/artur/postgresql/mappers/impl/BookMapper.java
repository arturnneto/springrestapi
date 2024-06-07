package com.artur.postgresql.mappers.impl;

import com.artur.postgresql.domain.dto.BookDto;
import com.artur.postgresql.domain.entities.BookEntity;
import com.artur.postgresql.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {
    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) { return modelMapper.map(bookDto, BookEntity.class);
    }
}
