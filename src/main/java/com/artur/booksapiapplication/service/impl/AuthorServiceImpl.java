package com.artur.booksapiapplication.service.impl;

import com.artur.booksapiapplication.domain.entities.AuthorEntity;
import com.artur.booksapiapplication.repositories.AuthorRepository;
import com.artur.booksapiapplication.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
