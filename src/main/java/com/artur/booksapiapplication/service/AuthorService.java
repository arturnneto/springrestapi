package com.artur.booksapiapplication.service;

import com.artur.booksapiapplication.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}

