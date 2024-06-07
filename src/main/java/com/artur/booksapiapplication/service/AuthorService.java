package com.artur.booksapiapplication.service;

import com.artur.booksapiapplication.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
}
