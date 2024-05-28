package com.artur.postgresql.dao;

import com.artur.postgresql.domain.Author;

import java.util.Optional;

public interface AuthorDao {

    void create(Author author);
    Optional<Author> findOne(long l);
}
