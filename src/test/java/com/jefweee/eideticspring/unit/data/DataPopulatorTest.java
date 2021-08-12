package com.jefweee.eideticspring.unit.data;

import com.jefweee.eideticspring.MongoDbIntegrationTest;
import com.jefweee.eideticspring.data.DataPopulator;
import com.jefweee.eideticspring.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class DataPopulatorTest extends MongoDbIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void canFetchAndStoreABookFromGoogle(){
        DataPopulator dataPopulator = new DataPopulator();
        dataPopulator.populateBooksFromGoogle(1);
        assertTrue(bookRepository.findAll().size() == 1);
    }
}
