package com.jefweee.eideticspring.domain;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    @Autowired
    MongoTemplate testMongoTemplate;

    @Test
    public void testCanSuccessfullyStoreAndRetrieveMultipleBooks() {

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Fried Green Tomatoes at the Whistlestop Cafe");
        testMongoTemplate.save(book1);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("The Invisible Library");
        testMongoTemplate.save(book2);

        List<Book> result = testMongoTemplate.findAll(Book.class);
        assertTrue(result.size() == 2);
        assertTrue(result.stream()
              .allMatch(
                      b ->b.getTitle().equals("Fried Green Tomatoes at the Whistlestop Cafe") ||
                             b.getTitle().equals("The Invisible Library")));
    }

}
