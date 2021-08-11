package com.jefweee.eideticspring.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class BookRepositoryTest extends EmbeddedMongoDbIntegrationTest{

    @Test
    public void canSuccessfullyStoreAndRetrieveMultipleBooks() {

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Fried Green Tomatoes at the Whistlestop Cafe");
        getMongoTemplate().save(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("The Invisible Library");
        getMongoTemplate().save(book2);

        List<Book> result = getMongoTemplate().findAll(Book.class);
        assertTrue(result.size() == 2);
        assertTrue(result.stream()
              .allMatch(
                      b ->b.getTitle().equals("Fried Green Tomatoes at the Whistlestop Cafe") ||
                             b.getTitle().equals("The Invisible Library")));
    }

}
