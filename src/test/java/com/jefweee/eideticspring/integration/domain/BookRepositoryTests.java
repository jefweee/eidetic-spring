package com.jefweee.eideticspring.integration.domain;

import com.jefweee.eideticspring.MongoDbIntegrationTest;
import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryTests extends MongoDbIntegrationTest {

    @Autowired
    BookRepository repo;

    @Test
    public void canSuccessfullyStoreAndRetrieveMultipleBooks() {

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Fried Green Tomatoes at the Whistlestop Cafe");
        repo.save(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("The Invisible Library");
        repo.save(book2);

        List<Book> result = repo.findAll();
        assertTrue(result.size() == 2);
        assertTrue(result.stream()
              .allMatch(
                      b ->b.getTitle().equals("Fried Green Tomatoes at the Whistlestop Cafe") ||
                             b.getTitle().equals("The Invisible Library")));
    }
}