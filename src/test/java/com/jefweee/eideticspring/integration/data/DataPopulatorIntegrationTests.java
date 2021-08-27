package com.jefweee.eideticspring.integration.data;

import com.jefweee.eideticspring.MongoDbIntegrationTest;
import com.jefweee.eideticspring.data.DataPopulator;
import com.jefweee.eideticspring.googleclient.exception.FailedToFetchBooksFromGoogleException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataPopulatorIntegrationTests extends MongoDbIntegrationTest {

    @Autowired
    DataPopulator dataPopulator;

    @Test
    public void canFetchAndStoreABookFromGoogle() throws FailedToFetchBooksFromGoogleException{
        dataPopulator.populateBooksFromGoogle(1);
        assertTrue(getBookRepository().findAll().size() == 1);
    }
}
