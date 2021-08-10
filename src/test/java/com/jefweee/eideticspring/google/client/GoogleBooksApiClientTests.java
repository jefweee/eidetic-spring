package com.jefweee.eideticspring.google.client;

import com.jefweee.eideticspring.google.json.GoogleBook;
import com.jefweee.eideticspring.google.json.GoogleBookResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GoogleBooksApiClientTests {

    Logger logger = LoggerFactory.getLogger(GoogleBooksApiClientTests.class);

    @Autowired
    GoogleBooksApiClient googleBooksApiClient;

    @Test
    public void testApiClientPropertiesConfiguredCorrectly(){
        assertThat(googleBooksApiClient.getGoogleBookApiKey(), is(notNullValue()));
        assertThat(googleBooksApiClient.getGoogleBookApiBaseUrl(), is("https://www.googleapis.com/books/v1"));
    }

    @Test
    public void testCantFetchBooksWithNegativeResultLimit(){
        int maxResults = -1;
        String printType = "books";
        String searchTerm = "flowers";

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
                    googleBooksApiClient.getBooks(searchTerm, printType, maxResults);
                }
        );

        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains("maxResults: must be greater than or equal to 1"));
    }

    @Test
    public void testCantFetchBooksWithZeroResultLimit(){
        int maxResults = 0;
        String printType = "books";
        String searchTerm = "flowers";

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
                    googleBooksApiClient.getBooks(searchTerm, printType, maxResults);
                }
        );

        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains("maxResults: must be greater than or equal to 1"));
    }

    @Test
    public void testCantFetchBooksWithoutSearchTerm(){
        int maxResults = 1;
        String printType = "books";
        String searchTerm = "";

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
                    googleBooksApiClient.getBooks(searchTerm, printType, maxResults);
                }
        );

        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains("searchTerm: must not be blank"));
    }

    @Test
    public void testCantFetchBooksWithoutPrintType(){
        int maxResults = 0;
        String printType = "";
        String searchTerm = "flowers";

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
                    googleBooksApiClient.getBooks(searchTerm, printType, maxResults);
                }
        );

        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains("printType: must not be blank"));
    }

    @Test
    public void testCanFetchOneBook(){
        int maxResults = 1;
        String printType = "books";
        String searchTerm = "flowers";

        GoogleBookResponse response = googleBooksApiClient.getBooks(searchTerm, printType, maxResults);

        assertThat(response.getItems().size(), is(1));
    }
}
