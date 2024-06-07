package com.artur.postgresql.service.impl;

import com.artur.postgresql.domain.entities.AuthorEntity;
import com.artur.postgresql.repositories.AuthorRepository;
import com.artur.postgresql.service.AuthorService;
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
