package com.artur.postgresql.dao;

import com.artur.postgresql.domain.Book;

public interface BookDao {
    void create(Book book);
}
