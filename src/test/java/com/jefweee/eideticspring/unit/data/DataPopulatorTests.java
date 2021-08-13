package com.jefweee.eideticspring.unit.data;

import com.jefweee.eideticspring.MongoDbIntegrationTest;
import com.jefweee.eideticspring.data.DataPopulator;
import com.jefweee.eideticspring.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataPopulatorTests extends MongoDbIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    DataPopulator dataPopulator;

    @Test
    public void canFetchAndStoreABookFromGoogle(){
        dataPopulator.populateBooksFromGoogle(1);
        assertTrue(bookRepository.findAll().size() == 1);
    }
}
