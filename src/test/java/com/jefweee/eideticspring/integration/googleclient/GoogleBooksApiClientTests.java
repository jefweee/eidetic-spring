package com.jefweee.eideticspring.integration.googleclient;

import com.jefweee.eideticspring.domain.Book;
import com.jefweee.eideticspring.googleclient.json.GoogleBook;
import com.jefweee.eideticspring.googleclient.json.GoogleBookResponse;
import com.jefweee.eideticspring.googleclient.IGoogleBooksApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    IGoogleBooksApiClient googleBooksApiClient;

    @Test
    public void apiClientPropertiesConfiguredCorrectly(){
        assertThat(googleBooksApiClient.getGoogleBookApiKey(), is(notNullValue()));
        assertThat(googleBooksApiClient.getGoogleBookApiBaseUrl(), is("https://www.googleapis.com/books/v1"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0})
    public void cantFetchBooksWithResultLimitZeroOrLower(int numBooksToFetch){
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
                    googleBooksApiClient.getFictionBooksFromGoogle(numBooksToFetch);
                }
        );
        String actualExceptionMessage = exception.getMessage();
        assertTrue(actualExceptionMessage.contains("numBooksToFetch: must be greater than or equal to 1"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 20, 23, 26})
    public void cantFetchMultipleBooks(int numBooksToFetch){
        List<GoogleBook> booksFromGoogle = googleBooksApiClient.getFictionBooksFromGoogle(numBooksToFetch);
        assertThat(booksFromGoogle.size(), is(numBooksToFetch));
    }

}
