package com.artur.postgresql.dao.impl;

import com.artur.postgresql.TestDataUtil;
import com.artur.postgresql.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTest {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTest(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.create(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(authorC);

        List<Author> result = underTest.findMany();
        assertThat(result)
                .hasSize(3)
                .containsExactly(author, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        author.setName("UPDATED");
        underTest.update(author.getId(), author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isEmpty();
    }
}
