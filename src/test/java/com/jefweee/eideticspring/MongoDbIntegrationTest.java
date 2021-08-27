package com.jefweee.eideticspring;

import com.jefweee.eideticspring.domain.BookRepository;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Category(IntegrationTest.class)
public abstract class MongoDbIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Container
    protected static MongoDBContainer database = new MongoDBContainer("mongo");

    @BeforeEach
    @AfterEach
    public void clearDownDatabase(){
        bookRepository.deleteAll();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", database::getReplicaSetUrl);
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }
}
