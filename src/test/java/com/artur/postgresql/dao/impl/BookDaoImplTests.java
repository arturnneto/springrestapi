package com.artur.postgresql.dao.impl;

import com.artur.postgresql.TestDataUtil;
import com.artur.postgresql.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook();

        underTest.create(book);

        Mockito.verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("9780132350884"), eq("Clean Code"), eq(1L)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne("9780132350884");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("9780132350884")
        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql() {
        underTest.findMany();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook();
        underTest.update("9780132350884", book);

        verify(jdbcTemplate).update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                "9780132350884", "Clean Code", 1L, "9780132350884"
        );
    }

    @Test
    public void testThatDeleteGenerateCorrectSql() {
        underTest.delete("9780132350884");
        verify(jdbcTemplate).update(
                "DELETE FROM books WHERE isbn = ?",
                "9780132350884"
        );
    }
}
